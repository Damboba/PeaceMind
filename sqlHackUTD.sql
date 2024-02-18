create database if not exists conditions;

use conditions;

create table sign_and_symptoms(
#symptom is basically the diagnosis, we are giving the client
Symptom varchar(255),

#location is the location of the pain
location varchar(255),

#type of pain is either sharp, dull/ache, itch, or burn
typePain varchar(255),

#pain level is either low, medium or high
PainLevel varchar(255),

#hydration is either or a yes or a no, for now
hydration varchar(255),

#complaints are what the user can type in a free response section, copy paste some stuff from the internet
Complaints varchar(255),

#specialty is the specialty of the doctor that we would recommend them to.
Specialty varchar(255)
);

insert into sign_and_symptoms(Symptom, location, typePain, PainLevel, hydration, Complaints, Specialty)
values
('Stomachache', 'abdomen', 'ache', 'low, medium', 'no','Viral gastroenteritis or strep throat Problems with internal organs of the abdomen: Persistent severe pain in abdomen', 'PrimaryCare'),
('Headache', 'head', 'sharp', 'high, medium, low', 'no', 'Lack of water, Lack of sleep A sharp sense of pain in the head', 'PrimaryCare/Neurologist'),
('Diarrhea', 'stomach', 'burn', 'high, medium', 'no', 'Fever, occasional vomiting, abdominal cramps', 'PrimaryCare'),
('Earache', 'ear', 'ache', 'low, medium', 'no', 'Fever, difficulty hearing, drainage, swelling around the ears', 'Otolaryngologist'),
('Sore Throat', 'neck', 'dull', 'low, medium', 'yes', 'Decreased appetite, increased drooling, viral', 'Otolaryngologist'),
('Cold', 'nose, throat, eyes' , 'itch, ache, burn', 'low, medium', 'yes', 'Runny nose, scratchy throat, coughing, sneezing, watery eyes', 'Otolaryngologist'),
('Cough', 'mouth' , 'dull, ache', 'low, medium', 'yes', 'Throat irritation, hoarse voice, sore throat, runny nose', 'PrimaryCare'),
('Noisy Breathing', 'nose, mouth' , 'ache', 'low, medium', 'no', 'Stuffy nose, sore throat, mild fever, noisy gasping, hard to breathe', 'Otolaryngologist'),
('Pinkeye', 'eye' , 'ache, itch, burn', 'medium, high', 'no', 'Bacterial infection, swollen eyes, allergic and chemical irritation', 'Otolaryngologist'),
('Mouth Sores', 'mouth' , 'ache', 'medium', 'yes', 'White patches on tongue, pain on swallowing, swollen lips', 'PrimaryCare'),
('Itching', 'face, back, arms, legs, neck' , 'ache, burn', 'low, medium', 'no', 'Blister-like spots(Chickenpox), Bald patches on scalp(Ringworm), Dry areas on body (eczema)', 'Dermatologist'),
('Rash', 'face, arms, legs, back, neck' , 'ache, burn, itch', 'low, medium', 'no', 'Minor skin infections, severe bacterial infections', 'Dermatologist'),
('Fever', 'face' , 'ache, burn', 'medium', 'yes', 'Elevation of body temperature, overheating, reaction to medication', 'PrimaryCare');
