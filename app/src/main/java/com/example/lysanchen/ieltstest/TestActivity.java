package com.example.lysanchen.ieltstest;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.lysanchen.ieltstest.models.Section;
import com.example.lysanchen.ieltstest.models.Subsection;
import com.example.lysanchen.ieltstest.services.AudioService;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class TestActivity extends AppCompatActivity implements DiagramQuestionFragment.OnFragmentInteractionListener {

    ProgressBar pb;
    Section sect;
    int index;
    int ssIndex;
    int qIndex;
    Subsection ss;
    List<Subsection> sslist;
    List<Question> qList;
    int score=0;
    DatabaseReference dbRef;
    int cycle=0;
    int lastquestion=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ssIndex = 0;
        qIndex =0;
        dbRef = FirebaseDatabase.getInstance().getReference();


        pb = (ProgressBar) findViewById(R.id.progressBar);
        pb.setMax(40);
        pb.setProgress(1);
        Intent intent2 = getIntent();
        sect =(Section) intent2.getExtras().get("section");
        Intent intent = new Intent(this, AudioService.class);
        intent.putExtra("url", sect.getAudio());
        startService(intent);
        prep();


    }


    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    public void prep(){
        ssIndex=0;
        HashMap<String, Subsection> ssmap = sect.getSubsection();
        sslist = new ArrayList<Subsection>(ssmap.values());
        ss = sslist.get(ssIndex);
        ss.getImage();
        qList = new ArrayList<Question>(ss.getQuestion().values());
        changeFragment(ss.getTypeofQuestion());
        cycle++;
    }

    public void next(){
            ssIndex++;

            if(ssIndex<sslist.size()){
                ss = sslist.get(ssIndex);
                qList = new ArrayList<Question>(ss.getQuestion().values());
                changeFragment(ss.getTypeofQuestion());
            }else{
                // push score

                    SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                    String loginemail = sharedPref.getString("loginemail", "");

                    dbRef.child("candidate").child(loginemail).child("attempts").child("1").child("score").setValue(score);
                    dbRef.child("candidate").child(loginemail).child("attempts").child("1").child("grade").setValue(getGrade(score));


                    AlertDialog.Builder builder = new AlertDialog.Builder(TestActivity.this);
                    builder.setTitle("End Test?");
                    builder.setMessage("You have reached the end of the test. Choose \"Okay\" to end the test");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(), "Test has ended. Your results have been recorded.", Toast.LENGTH_SHORT).show();
                            Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent2);
                            Intent intent = new Intent(getApplicationContext(), AudioService.class);
                            stopService(intent);
                        }
                    });

//                    if (cycle < 2) {
//                        builder.setNegativeButton("Continue Test", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Intent intent = new Intent(getApplicationContext(), AudioService.class);
//                                startService(intent);
//                                prep();
//                            }
//                        });
//                    }
                builder.show();
            }


    }

    public void save(List<String> answers){

        for(int i=0; i<qList.size();i++){
            qList.get(i).setChosenAnswer(answers.get(i));
             Question q = qList.get(i);
            if(q.getAnswerText().toLowerCase().equals(q.getChosenAnswer().toLowerCase())){
                score++;
            }

        }

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String loginemail= sharedPref.getString("loginemail", "");
        dbRef.child("candidate").child(loginemail).child("attempts").child("1").child("record").push().setValue(qList);
        next();

    }


    public void changeFragment(String cat){

        if(cat.equals("Form, Note, Table")){
            getSupportFragmentManager().beginTransaction().replace(R.id.frameQ, DiagramQuestionFragment.newInstance(ss,"")).commit();

        }else if (cat.equals("Plan, Map, Diagram labeling")){
            getSupportFragmentManager().beginTransaction().replace(R.id.frameQ, DiagramQuestionFragment.newInstance(ss,"")).commit();

        }else if (cat.equals("Plan, Map, Diagram labeling")){
            getSupportFragmentManager().beginTransaction().replace(R.id.frameQ, DiagramQuestionFragment.newInstance(ss,"")).commit();

        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String loginemail= sharedPref.getString("loginemail", "");
        dbRef.child("candidate").child(loginemail).child("attempts").child("1").child("log").push().setValue("LEFT APP at "+ new Date().toString());

    }

    public void plusLast(){
        lastquestion++;
    }
    public int getLast(){
        return lastquestion;
    }

    public String getGrade(int score){
        String grade = "A";

        if(score>=36){
            grade= "A";
        }else if(score>=32){
            grade= "B";
        }else if(score>=28){
            grade= "C";
        }else if(score>=24){
            grade= "D";
        }else {
            grade= "F";
        }

        return grade;
    }
}
