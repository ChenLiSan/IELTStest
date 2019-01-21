package com.example.lysanchen.ieltstest;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothHeadset;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.lysanchen.ieltstest.restmodels.Section;
import com.example.lysanchen.ieltstest.restmodels.Session;
import com.example.lysanchen.ieltstest.restservice.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import com.instacart.library.truetime.TrueTimeRx;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.lysanchen.ieltstest.restmodels.*;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NotifyFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NotifyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotifyFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    Button buttonStart;

    private OnFragmentInteractionListener mListener;

    public NotifyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotifyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotifyFragment newInstance(String param1, String param2) {
        NotifyFragment fragment = new NotifyFragment();
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
        View v =  inflater.inflate(R.layout.fragment_notify, container, false);

        buttonStart = (Button) v.findViewById(R.id.button2);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                StartTest(v);
            }
        });

        TrueTimeRx.build()
                .initializeRx("time.google.com")
                .subscribeOn(Schedulers.io())
                .subscribe(date -> {
                    Log.v("", "TrueTime was initialized and we have a time: " + date);
                }, throwable -> {
                    throwable.printStackTrace();
                });

        //testAPI();

        return v;
    }

    public View StartTest(View v){
        if (checkDevices()) {
             getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, LoadQuestionFragment.newInstance("","")).commit();

        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
            builder.setTitle("Plug In Headset ");
            builder.setMessage("It is recommended that you plug in your headset  before you start the test.");
            builder.setCancelable(false);
            builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getActivity().getApplicationContext(), "Plug in headphones and try again.", Toast.LENGTH_SHORT).show();
                }
            });

            // Remove this later to disallow skipping plugging headphones
            builder.setNegativeButton("No, proceed.", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, LoadQuestionFragment.newInstance("","")).commit();
                }
            });

            builder.show();
        }

        return v;
    }

    public boolean checkDevices(){
        return (isBluetoothHeadsetConnected() || isHeadphonesPlugged() ||isHeadphonesPlugged2());
    }

    public static boolean isBluetoothHeadsetConnected() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()
                && mBluetoothAdapter.getProfileConnectionState(BluetoothHeadset.HEADSET) == BluetoothHeadset.STATE_CONNECTED;
    }

    @TargetApi(23)
    private boolean isHeadphonesPlugged(){
        AudioManager audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        AudioDeviceInfo[] audioDevices = audioManager.getDevices(AudioManager.GET_DEVICES_ALL);
        for(AudioDeviceInfo deviceInfo : audioDevices){
            if(deviceInfo.getType()==AudioDeviceInfo.TYPE_WIRED_HEADPHONES
                    || deviceInfo.getType()==AudioDeviceInfo.TYPE_WIRED_HEADSET){
                return true;
            }
        }
        return false;
    }

    private boolean isHeadphonesPlugged2(){
        AudioManager audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        return audioManager.isWiredHeadsetOn();
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

    public boolean checkTime(){

        boolean ok =false;





        return ok;
    }

    public void testAPI(){
        RESTObject ob = new RESTObject();
        ob.setUrl("api/sessions");
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
            Toast.makeText(getActivity().getApplicationContext(), "Success."+o.getObj().toString(), Toast.LENGTH_SHORT).show();
        }
    }

    public static RESTObject Connect(RESTObject ro){

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

    public static boolean writeData (String data, HttpURLConnection conn){

        try{
            conn.setDoOutput(true);
            conn.getOutputStream().write(data.getBytes());
            return true;
        }catch (Exception ex){

            return false;
        }

    }

//    public void testREST(){
//
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://localhost:64611/")
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        UserService service = retrofit.create(UserService.class);
//        Call<List<Session>> call = service.sessions();
//
//        call.enqueue(new Callback<List<Session>>() {
//            @Override
//            public void onResponse(Call<List<Session>> call, Response<List<Session>> response) {
//
//            }
//
//            @Override
//            public void onFailure(Call<List<Session>> call, Throwable t) {
//
//            }
//        });
//
//
//    }
}
