package com.example.pmserved;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.EditText;
import  java.net.Socket;
import java.io.*;
import java.net.*;

public class MainActivity extends AppCompatActivity {

    boolean headRegion = false;
    boolean eyes = false;
    boolean forehead = false;
    boolean nose = false;
    boolean mouth = false;
    boolean torsoRegion = false;
    boolean chest = false;
    boolean heart = false;
    boolean abdomen = false;
    boolean arm = false;
    boolean leg = false;
    boolean sharp = false;
    boolean dull = false;
    boolean burn = false;
    boolean low = false;
    boolean mid = false;
    boolean high = false;

    String pythonMess = "";
    String pythonBack = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton mainHeadButton = findViewById(R.id.imageHead);
        ImageButton mainTorsoButton = findViewById(R.id.imageTorse);
        Button mainProceedButton = findViewById(R.id.proceedMain);

        mainHeadButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mainHeadButton.setBackgroundColor(Color.BLUE);
                if (headRegion) {
                    headRegion = false;
                }
                else { headRegion = true; }
            }
        });

        mainTorsoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (torsoRegion) { torsoRegion = false; }
                else { torsoRegion = true; }
            }
        });

        mainProceedButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (headRegion) { headCheck(); }
                else if (torsoRegion) { torsoCheck(); }
            }
        });
    }

    public void headCheck() {
        setContentView(R.layout.head);

        ImageButton eyesButton = findViewById(R.id.imageEyes);
        ImageButton foreheadButton = findViewById(R.id.imageForehead);
        ImageButton noseButton = findViewById(R.id.imageNose);
        ImageButton mouthButton = findViewById(R.id.imageMouth);
        Button proceedHeadButton = findViewById(R.id.proceedHead);

        eyesButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (eyes) { eyes = false; }
                else { eyes = true; }
            }
        });

        foreheadButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (forehead) { forehead = false; foreheadButton.setBackgroundColor(Color.BLUE);}
                else { forehead = true; }
            }
        });

        noseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (nose) { nose = false; }
                else { nose = true; }
            }
        });

        mouthButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mouth) { mouth = false; }
                else { mouth = true; }
            }
        });

        proceedHeadButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (eyes) { pythonMess += "eyes;"; }
                else if (forehead) { pythonMess += "forehead;"; }
                else if (nose) { pythonMess += "nose;"; }
                else if (mouth) { pythonMess += "mouth;"; }
                surveyCheck();
            }
        });
        return;
    }

    public void torsoCheck() {
        setContentView(R.layout.torso);

        ImageButton chestButton = findViewById(R.id.imageChest);
        ImageButton heartButton = findViewById(R.id.imageHeart);
        ImageButton abdomenButton = findViewById(R.id.imageAbdomen);
        Button proceedTorsoButton = findViewById(R.id.proceedTorso);

        chestButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (chest) { chest = false; }
                else { chest = true; }
            }
        });

        heartButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (heart) { heart = false; }
                else { heart = true; }
            }
        });

        abdomenButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (abdomen) { abdomen = false; }
                else { abdomen = true; }
            }
        });

        proceedTorsoButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (chest) { pythonMess += "chest;"; }
                else if (heart) { pythonMess += "heart;"; }
                else if (abdomen) { pythonMess += "abdomen;"; }
                surveyCheck();
            }
        });
        return;
    }

    public void surveyCheck() {
        setContentView(R.layout.survey);

        Button sharpButton = findViewById(R.id.buttonSharp);
        Button dullButton = findViewById(R.id.buttonAche);
        Button burnButton = findViewById(R.id.buttonBurn);
        Button lowPainButton = findViewById(R.id.buttonLow);
        Button midPainButton = findViewById(R.id.buttonMid);
        Button highPainButton = findViewById(R.id.buttonHigh);
        EditText detailsReceived = findViewById(R.id.detailsInput);
        Button submitButton = findViewById(R.id.submit);

        sharpButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (sharp) { sharp = false; }
                else { sharp = true; }
            }
        });

        dullButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (dull) { dull = false; }
                else { dull = true; }
            }
        });

        burnButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (burn) { burn = false; }
                else { burn = true; }
            }
        });

        lowPainButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (low) { low = false; }
                else { low = true; }
            }
        });

        midPainButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (mid) { mid = false; }
                else { mid = true; }
            }
        });

        highPainButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (high) { high = false; }
                else { high = true; }
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (sharp) { pythonMess += "sharp;"; }
                else if (dull) { pythonMess += "dull/ache;"; }
                else if (burn) { pythonMess += "burn;"; }

                if (low) { pythonMess += "tolerable;"; }
                else if (mid) { pythonMess += "moderate;"; }
                else if (high) { pythonMess += "excruciating;"; }

                pythonMess += "yes;";
                pythonMess += detailsReceived.getText().toString() + ";";
                showResults();
            }
        });
        return;
    }

    public void showResults() {
        setContentView(R.layout.results);
        TextView resultsView = findViewById(R.id.resultsText);

        String serverHostname = "127.0.0.1";
        int port = 1234;

        try (
                Socket socket = new Socket(serverHostname, port);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in))
        ) {
            while ((pythonMess = stdIn.readLine()) != null) {
                out.println(pythonMess);
                pythonBack = in.readLine();
                resultsView.setText(pythonBack);
            }
            socket.close();
        } catch (UnknownHostException e) {
            System.err.println("Unknown host: " + serverHostname);
        } catch (IOException e) {
            System.err.println("I/O Exception occurred: " + e.getMessage());
        }
    }
}