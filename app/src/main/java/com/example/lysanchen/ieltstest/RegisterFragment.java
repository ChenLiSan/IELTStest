package com.example.lysanchen.ieltstest;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lysanchen.ieltstest.models.Section;
//import com.example.lysanchen.ieltstest.restmodels.Session;
import com.example.lysanchen.ieltstest.models.Session;
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
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegisterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private String selectedSession ="";
    private Button buttonRegister;
    Spinner spinner;
    Boolean  mSpinnerInitialized = false;
    Session[] sessions;
    List<Session> sessionList;
    TextView textView2;
    DatabaseReference mDatabase;
    DatabaseReference ref;
    int sessionindex;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
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

        View v = inflater.inflate(R.layout.fragment_register, container, false);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        buttonRegister = (Button) v.findViewById(R.id.button);
        buttonRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Register();
            }
        });

        spinner = (Spinner) v.findViewById(R.id.spinner);
        //getSessiosn();
        getSession();


        spinner.setOnItemSelectedListener(this);
        textView2 = (TextView) v.findViewById(R.id.textView2);

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        selectedSession= sharedPref.getString("selectedSession", selectedSession);

        if(!selectedSession.equals("")){
            spinner.setVisibility(View.INVISIBLE);
            buttonRegister.setVisibility(View.INVISIBLE);
            textView2.setText("You been registered for session at  "+ selectedSession);
        }
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        selectedSession = (String) parent.getItemAtPosition(pos);
        sessionindex = pos;

    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    public void Register(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle("Confirm Registration");
        builder.setMessage(selectedSession+" session selected. Do you  want to proceed ?");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Toast.makeText(getActivity().getApplicationContext(), "You have been registered.", Toast.LENGTH_SHORT).show();
                SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("selectedSession", selectedSession);
                editor.putString("sessionid", sessionList.get(sessionindex).getSessionid());
                editor.commit();

                if(true){
                    spinner.setVisibility(View.INVISIBLE);
                    buttonRegister.setVisibility(View.INVISIBLE);
                    textView2.setText("You been registered for session at  "+ selectedSession);
                    textView2.setVisibility(View.VISIBLE);
                }


            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity().getApplicationContext(), "You have not registered for any session.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.show();
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


    public void getSessiosn(){
        RESTObject ob = new RESTObject();
        ob.setUrl("api/sessions");
        ob.setOperation("GET");
        new HttpAsyncTask().execute(ob);

    }

    public void getSession(){
        ref = FirebaseDatabase.getInstance().getReference().child("session");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try {
                    sessionList = new ArrayList<Session>();
                    List<String> list = new ArrayList<String>();
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Session s = postSnapshot.getValue(Session.class);
                        sessionList.add(s);

                        list.add(s.getDate().toString() + " " + s.getStartTime());
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, list);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    spinner.setAdapter(adapter);
                }catch (Exception ex){
                    ex.getMessage();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.w("TAG", "Data Unretrived", databaseError.toException());
            }
        };
        ref.addValueEventListener(postListener);
    }

    public void writeAttempt(){
        RESTObject ob = new RESTObject();
        ob.setUrl("api/sessions");
        ob.setOperation("GET");
        new HttpAsyncTask2().execute(ob);

    }

public class HttpAsyncTask extends AsyncTask<RESTObject, Void, RESTObject> {
    @Override
    protected RESTObject doInBackground(RESTObject... urls) {

        return Connect(urls[0]);
    }

    @Override
    protected void onPostExecute(RESTObject o) {
        sessions = (Session[]) o.getObj();

        List<String> list = new ArrayList<String>();

        for(int i=0; i<sessions.length;i++){
            String f  = sessions[i].getDate().substring(0,10);
            f+=" "+ sessions[i].getStartTime();
            list.add(f);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

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

    public class HttpAsyncTask2 extends AsyncTask<RESTObject, Void, RESTObject> {
        @Override
        protected RESTObject doInBackground(RESTObject... urls) {

            return Connect(urls[0]);
        }

        @Override
        protected void onPostExecute(RESTObject o) {
            sessions = (Session[]) o.getObj();

            List<String> list = new ArrayList<String>();

            for(int i=0; i<sessions.length;i++){
                String f  = sessions[i].getDate().substring(0,10);
                f+=" "+ sessions[i].getStartTime();
                list.add(f);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item,list);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

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