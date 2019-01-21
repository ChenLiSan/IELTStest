package com.example.lysanchen.ieltstest;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.lysanchen.ieltstest.models.*;
import com.example.lysanchen.ieltstest.restmodels.Session;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ResultsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ResultsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResultsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Candidate candidate;
    TextView tx;
    TextView tx2;
    String loginemail;
    DatabaseReference mDatabase;
    List<Attempt> attempts;

    private OnFragmentInteractionListener mListener;

    public ResultsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResultsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResultsFragment newInstance(String param1, String param2) {
        ResultsFragment fragment = new ResultsFragment();
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
        View v =  inflater.inflate(R.layout.fragment_results, container, false);
        tx = v.findViewById(R.id.textView3);
        tx2 = v.findViewById(R.id.textView15);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        checkLogin();

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

    public void checkLogin(){
        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        loginemail= sharedPref.getString("loginemail", "");

        if(loginemail.equals("")){
            tx.setText("Please login first!");
            tx2.setText("");
        }
        else{

            getAttempts();
        }
    }

    public void getAttempts(){
        DatabaseReference ref = mDatabase.child("candidate").child(loginemail);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                Candidate c = snapshot.getValue(Candidate.class);
                attempts = c.getAttempt();
                Attempt last = attempts.get(attempts.size()-1);
                tx.setText("You scored "+last.getScore()+" out of 40 in your last test.");
                tx2.setText(last.getGrade());

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getResultNOT(){
        RESTObject ob = new RESTObject();
        ob.setUrl("api/candidate");
        ob.setOperation("GET");
        new HttpAsyncTask().execute(ob);

    }

    public class HttpAsyncTask extends AsyncTask<RESTObject, Void, RESTObject> {
        @Override
        protected RESTObject doInBackground(RESTObject... urls) {

            return Connect(urls[0]);
        }

        @Override
        protected void onPostExecute(RESTObject o) {
            candidate = (Candidate) o.getObjd();
        }

        public  RESTObject Connect(RESTObject ro){

            try{
                java.net.URL url1 = new java.net.URL(ro.getFullUrl());

                HttpURLConnection myConnection = (HttpURLConnection) url1.openConnection();

                if(ro.getOperation().toUpperCase().equals("GET")){
                    //
                }else{
                    myConnection.setRequestMethod(ro.getOperation());
                    writeData(ro.getData(), myConnection);
                }

                if (myConnection.getResponseCode() == 200) {
                    InputStream responseBody = myConnection.getInputStream();
                    InputStreamReader responseBodyReader =  new InputStreamReader(responseBody, "UTF-8");
                    JsonReader jsonReader = new JsonReader(responseBodyReader);

                    Gson gson = new Gson();
                    ro.setObj(gson.fromJson(jsonReader, com.example.lysanchen.ieltstest.restmodels.Session[].class));

                    //if(ro.getObj().getClass().equals(Session.class)){

                    // }


                } else {
                    ro.setObj(null);

                }
            }
            catch (Exception ex){
                String fv = ex.getMessage();
                String fg ="";
            }

            return ro;
        }

        public  boolean writeData (String data, HttpURLConnection conn){

            try{
                conn.setDoOutput(true);
                conn.getOutputStream().write(data.getBytes());
                return true;
            }catch (Exception ex){

                return false;
            }

        }
    }
}
