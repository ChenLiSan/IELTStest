package com.example.lysanchen.ieltstest;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.lysanchen.ieltstest.models.Paper;
import com.example.lysanchen.ieltstest.models.Section;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoadQuestionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoadQuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoadQuestionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private DatabaseReference mDatabase;
    private DatabaseReference ref;
    private static Section sect;
    private static Paper paper;
    ProgressBar mProgressBar;
    CountDownTimer mCountDownTimer;
    Button buttonSt;

    private OnFragmentInteractionListener mListener;

    public LoadQuestionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoadQuestionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoadQuestionFragment newInstance(String param1, String param2) {
        LoadQuestionFragment fragment = new LoadQuestionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_load_question, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        loadQuestionTest();

        mProgressBar=(ProgressBar)v.findViewById(R.id.progressBar3);
        buttonSt = v.findViewById(R.id.button6);

        buttonSt.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startTest();
            }
        });
        mProgressBar.setProgress(0);


        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    //Actual code
    public void start(){

        Intent actIntent = new Intent(this.getContext(), TestActivity.class);
        actIntent.putExtra("paper", paper);
        getActivity().startActivity(actIntent);
    }

    // Testing code
    public void startTest(){

        Intent actIntent = new Intent(this.getContext(), TestActivity.class);
        actIntent.putExtra("section", sect);
        getActivity().startActivity(actIntent);
    }

    //Testing code
    public void loadQuestionTest(){
            ref = FirebaseDatabase.getInstance().getReference().child("section").child("section1").child("-LWP0kCiHQ7GdWDg0pZ7");

            ValueEventListener postListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    try {
                        sect = dataSnapshot.getValue(Section.class);
                        progress();
                    }catch (Exception ex){

                            ex.getMessage();
                        }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Post failed, log a message
                    Log.w("TAG", "Data Unretrived", databaseError.toException());
                }
            };
            ref.addValueEventListener(postListener);

    }

    //Actual code
    public void loadQuestion(){

        paper = new Paper();
        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("section").child("section1").child("1");
        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference().child("section").child("section2").child("1");;
        DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference().child("section").child("section3").child("1");;
        DatabaseReference ref4 = FirebaseDatabase.getInstance().getReference().child("section").child("section4").child("1");;

        ValueEventListener sectList1 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Section sect1  = dataSnapshot.getValue(Section.class);
                paper.setSection1(sect1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("TAG", "Data Unretrived", databaseError.toException());
            }
        };

        ValueEventListener sectList2 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Section sect2  = dataSnapshot.getValue(Section.class);
                paper.setSection1(sect2);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("TAG", "Data Unretrived", databaseError.toException());
            }
        };

        ValueEventListener sectList3 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Section sect3  = dataSnapshot.getValue(Section.class);
                paper.setSection1(sect3);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("TAG", "Data Unretrived", databaseError.toException());
            }
        };

        ValueEventListener sectList4 = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Section sect4  = dataSnapshot.getValue(Section.class);
                paper.setSection1(sect4);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("TAG", "Data Unretrived", databaseError.toException());
            }
        };

        ref1.addValueEventListener(sectList1);
        ref2.addValueEventListener(sectList2);
        ref3.addValueEventListener(sectList3);
        ref4.addValueEventListener(sectList4);
    }

    public void progress(){

        mCountDownTimer=new CountDownTimer(10000,1000) {

            @Override
            public void onTick(long millisUntilFinished) {


                mProgressBar.setProgress(mProgressBar.getProgress()+5);

            }

            @Override
            public void onFinish() {

                buttonSt.setVisibility(View.VISIBLE);
                mProgressBar.setProgress(100);
            }
        };
        mCountDownTimer.start();
    }
}
