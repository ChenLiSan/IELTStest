package com.example.lysanchen.ieltstest;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lysanchen.ieltstest.models.Candidate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AccountFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private DatabaseReference mDatabase;
    EditText etName;
    EditText etEmail;
    EditText etPassword;
    EditText etId;
    Button buttonReg;
    Button buttonLog;
    TextView tx;



    private OnFragmentInteractionListener mListener;

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
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
        View v =  inflater.inflate(R.layout.fragment_account, container, false);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        tx = v.findViewById(R.id.textView6);
        etName = v.findViewById(R.id.editText3);
        etEmail = v.findViewById(R.id.editText4);
        etPassword = v.findViewById(R.id.editText5);
        etId = v.findViewById(R.id.editText6);
        buttonReg =v.findViewById(R.id.button4);


        buttonReg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                RegisterAccount();
            }
        });

        buttonLog =v.findViewById(R.id.button8);

        buttonLog.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((MainActivity)getActivity()).changeFrag(1);
            }
        });

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

    public void RegisterAccount(){

        Candidate c1 = new Candidate(etName.getText().toString(),
                etEmail.getText().toString(),
                etPassword.getText().toString(),
                etId.getText().toString());

        mDatabase.child("candidate").child(c1.getEmail()).setValue(c1);
        ((MainActivity)getActivity()).changeFrag(1);


    }

    public void checkLogin(){

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        String loginemail= sharedPref.getString("loginemail", "");

        if(!loginemail.equals("")){
            DatabaseReference ref = mDatabase.child("candidate").child(loginemail);
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    try {

                        Candidate c = snapshot.getValue(Candidate.class);


                        tx.setText("Account Details");


                        etEmail.setText("Email: " + c.getEmail());
                        etPassword.setText("Password: Hidden");
                        etId.setText("Student ID: " + c.getId());
                        etName.setText("Name: " + c.getName());

                        etEmail.setEnabled(false);
                        etPassword.setEnabled(false);
                        etId.setEnabled(false);
                        etName.setEnabled(false);

                        buttonLog.setVisibility(View.INVISIBLE);
                        buttonReg.setVisibility(View.VISIBLE);
                        buttonReg.setText("Logout");
                        buttonReg.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                Logout();
                            }
                        });
                    }catch ( Exception ex){
                        ex.getMessage();
                    }

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {


                }
            });
        }
        else{

            tx.setText("Register For Account");
            etEmail.setText("");
            etPassword.setText("");
            etId.setText("");
            etName.setText("");

            etEmail.setEnabled(true);
            etPassword.setEnabled(true);
            etId.setEnabled(true);
            etName.setEnabled(true);

            buttonLog.setVisibility(View.VISIBLE);
            buttonReg.setVisibility(View.VISIBLE);
            buttonReg.setText("Register");
            buttonReg.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    RegisterAccount();
                }
            });
        }


    }

    public void Logout(){

        SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("selectedSession", "");
        editor.putString("loginemail", "");
        editor.commit();
        Toast.makeText(getContext(), "Logged out succesfully!", Toast.LENGTH_SHORT).show();
        ((MainActivity)getActivity()).changeFrag(1);
    }

}
