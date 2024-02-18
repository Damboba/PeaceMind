from dotenv import load_dotenv
import os
from langchain.sql_database import SQLDatabase
from langchain.agents import create_sql_agent
from langchain_community.agent_toolkits import SQLDatabaseToolkit
from langchain_openai import OpenAI
from langchain.prompts.chat import ChatPromptTemplate
from langchain.prompts import PromptTemplate
from sqlalchemy import create_engine
import socket
import threading
import json

load_dotenv()

HEADER_INITIAL = 25
#connecting to the app
port= 1234
server = '127.0.0.1'
print(server)
addr = (server,port)
FORMAT = 'utf-8'


with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
    s.bind((server, port))
    s.listen()
    print("server is listening on {}:{}".format(server, port))
    conn, address = s.accept()

    with conn:
        (print("connected by", addr))
        while True:
            data = conn.recv(1024)
            if not data:
                break
            print("received:", data.decode())
            conn.sendall(data)     

def analyze(str):
#temperature 0 makes it so llm isn't as 'creative'
    llm = OpenAI(temperature=0)
    #password for the database
    #password = os.environ.get('DB_PASS')
    password = ''

    #establish the database connection
    engine = create_engine("mysql://root:{pwd}@localhost/conditions".format(pwd = password))
    docEngine = create_engine("mysql://root:{pwd}@localhost/doctors".format(pwd = password))
    dbs = SQLDatabase(engine)
    docDbs = SQLDatabase(docEngine)
    sql_toolkit = SQLDatabaseToolkit(db = dbs, llm = llm)
    sql_toolkit.get_tools()
    doc_toolkit = SQLDatabaseToolkit(db = docDbs, llm = llm)
    doc_toolkit.get_tools()

    #gettingInput = 'head; blunt; low; no'
    gettingInput = str
    #getting the input from the user, and sending it to SQL
    conditions = gettingInput.split(';')
    location = conditions[0]
    typePain = conditions[1]
    PainLevel = conditions[2]
    hydration = conditions[3]
    if(len(conditions) == 5):
        Complaints = conditions[4]
    else:
        Complaints = ""

    "check the location coloumn for "+location+ ", check the typePain coloumn for "+ typePain+ ", Check the painLevel coloumn for " + PainLevel+ ", Check the hydration coloumn for " + hydration + ", Check the Complaints coloumn for " + Complaints +""
    if(len(conditions) > 4):
        prompt = ChatPromptTemplate.from_messages([
        #the prompt template for getting the users symptoms and conditions
        ("system", "DO NOT CHECK IF THE TABLE EXISTS, it is guaranteed to exist. DO NOT DOUBLE CHECK ANYTHING,\
        EVERYTHING IS CORRECT, YOU ARE A GOD AT THIS AND NEVER MAKE MISTAKES.\
        In the table sign_and_symptoms. Use the Like query instead of =,\
        Using the location coloumn for {location2}, and the typePain coloumn for\
        {typePain2} , or the painLevel coloumn for {PainLevel2}, or the hydration coloumn for\
        {hydration2}, or the Complaints coloumn for {Complaints2}\
        which Symptom best fits them all?\
        "),
        ("user", "YOU HAVE TO RETURN SOMETHING FROM THE Symptom coloumn, do not base it on the user input. one word only")
        ])
    else:
        prompt = ChatPromptTemplate.from_messages([
        #the prompt template for getting the users symptoms and conditions
        ("system", "DO NOT CHECK IF THE TABLE EXISTS, it is guaranteed to exist. DO NOT DOUBLE CHECK ANYTHING,\
        EVERYTHING IS CORRECT, YOU ARE A GOD AT THIS AND NEVER MAKE MISTAKES.\
        In the table sign_and_symptoms. Use the Like query instead of =,\
        Using the location coloumn for {location2}, and the typePain coloumn for\
        {typePain2} , or the painLevel coloumn for {PainLevel2}, or the hydration coloumn for\
        {hydration2} \
        which Symptom best fits them all?\
        "),
        ("user", "YOU HAVE TO RETURN SOMETHING FROM THE Symptom coloumn, do not base it on the user input. one word only")
        ])
    #userQuestion = input("Describe your issue.\n")

    specialPrompt = ChatPromptTemplate.from_messages([
    #the prompt template for getting the users symptoms and conditions
        ("system", "DO NOT CHECK IF THE TABLE EXISTS, it is guaranteed to exist. DO NOT DOUBLE CHECK ANYTHING,\
        EVERYTHING IS CORRECT, YOU ARE A GOD AT THIS AND NEVER MAKE MISTAKES.\
        In the table sign_and_symptoms"),
        ("user", "{question}")
        ])

    #the prompt template for matching the condition or symptoms with the corresponding doctor
    docPrompt = ChatPromptTemplate.from_messages([
        ("system", "DO NOT CHECK IF THE TABLE EXISTS, it is guaranteed to exist. DO NOT DOUBLE CHECK ANYTHING,\
        EVERYTHING IS CORRECT, YOU ARE A GOD AT THIS AND NEVER MAKE MISTAKES. Check the docInfo table.\
        Check the Specialty coloumn for what the user inputs, \
        and give The fullName, Phone, and Cost the information from the corresponding row of the Specialty."),
        ("user", "{mySymptom}")
    ])

    #agent to run sql queries
    agent = create_sql_agent(llm = llm, toolkit=sql_toolkit, verbose = False)
    if(len(conditions) > 4):   
        response = agent.invoke(prompt.format_prompt(question="", location2 = location, typePain2 = typePain, PainLevel2 = PainLevel, hydration2 = hydration, Complaints2 = Complaints))
    else:
        response = agent.invoke(prompt.format_prompt(question="", location2 = location, typePain2 = typePain, PainLevel2 = PainLevel, hydration2 = hydration))

    #prints to response to what the user's complaints are
    symptom = response["output"]
    print(symptom)

    #[]
    specialAgent = create_sql_agent(llm = llm, toolkit=sql_toolkit, verbose = False)
    #get the specialty field for the doctor from the symptoms list
    specialQuestion = "What is the Specialty for " + symptom 
    specialResponse = specialAgent.invoke(specialPrompt.format_prompt(question=specialQuestion))
    #prints to response to what the user's complaints are
    special = specialResponse["output"]
    print(special)

    #agent to run sql query to find the right doctor
    docAgent = create_sql_agent(llm = llm, toolkit=doc_toolkit, verbose = False)
    docter = docAgent.invoke(docPrompt.format_prompt(mySymptom = special))
    docSymptom = docter["output"]
    print(docSymptom)
    return ("The problem you are having could be a "+symptom+ " you should visit "+special+" The information for a doctor that specializes in that field is "+ docSymptom)