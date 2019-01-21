//package com.example.lysanchen.ieltstest;
//
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.util.Base64;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ProgressBar;
//
//import com.example.lysanchen.ieltstest.models.Section;
//import com.example.lysanchen.ieltstest.models.Subsection;
//
//import java.io.ByteArrayOutputStream;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//
//
///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link LoadingFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link LoadingFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class LoadingFragment extends Fragment {
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//    Button buttonProceed;
//    ProgressBar pb;
//    DummyData dd;
//    Section section;
//    static Section[] examSections;
//
//    private OnFragmentInteractionListener mListener;
//
//    public LoadingFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment LoadingFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static LoadingFragment newInstance(String param1, String param2) {
//        LoadingFragment fragment = new LoadingFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        examSections = new Section[4];
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//         View v=  inflater.inflate(R.layout.fragment_loading, container, false);
//
//         dd = new DummyData();
//
//         section = dd.getSection();
//         List<Subsection> ssList;
//                 //= section.getSubsection();
//
//        buttonProceed = (Button) v.findViewById(R.id.button3);
//        buttonProceed.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                StartTest();
//            }
//        });
//        buttonProceed.setVisibility(View.INVISIBLE);
//
//        pb = (ProgressBar) v.findViewById(R.id.progressBar2);
//        pb.setProgress(0);
//
//         for(int i=0; i<ssList.size();i++){
//             Subsection ss = ssList.get(i);
//             if(!ss.getImage().equals("") && ss.getImage()!=null) {
//                 new DownloadImage().execute(ss);
//
//             }
//         }
//        return v;
//    }
//
//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    public void StartTest(){
//        Intent actIntent = new Intent(this.getContext(), TestActivity.class);
//        actIntent.putExtra("section", section);
//        getActivity().startActivity(actIntent);
//
//    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
//
//    private String convertToBase64(Bitmap bitmap) {
//
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//
//        byte[] byteArrayImage = baos.toByteArray();
//
//        String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
//
//        return encodedImage;
//
//    }
//
//    private class DownloadImage extends AsyncTask<Subsection, Void, Subsection> {
//        private String TAG = "DownloadImage";
//        Subsection ques;
//
//        @Override
//        protected Subsection doInBackground(Subsection... params) {
//            Bitmap bitmap =  null;
//            try {
//                URL url = new URL(params[0].getImage());
//                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//                connection.setDoInput(true);
//                connection.connect();
//                InputStream input = connection.getInputStream();
//                bitmap = BitmapFactory.decodeStream(input);
//            } catch (Exception e) {
//                Log.e("Error", e.getMessage());
//                e.printStackTrace();
//            }
//            if(bitmap!=null) {
//                params[0].setImageStr(convertToBase64(bitmap));
//            }
//
//            return params[0];
//
//        }
//
//        protected void onPostExecute(Subsection result) {
//
//            if(result.getImageStr()!=null){
//                pb.setVisibility(View.INVISIBLE);
//                buttonProceed.setVisibility(View.VISIBLE);
//            }
//        }
//    }
//
//    public void generateSections(){
//
//
//    }
//
//
//
//
//
//}
