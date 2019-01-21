package com.example.lysanchen.ieltstest;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.lysanchen.ieltstest.models.*;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DiagramQuestionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DiagramQuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiagramQuestionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Subsection mParam1;
    private String mParam2;

    private ImageView image;
    TableLayout tab;
    PhotoViewAttacher pva;


    private OnFragmentInteractionListener mListener;

    float[] lastEvent = null;
    float d = 0f;
    float newRot = 0f;
    private boolean isZoomAndRotate;
    private boolean isOutSide;
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private int mode = NONE;
    private PointF start = new PointF();
    private PointF mid = new PointF();
    float oldDist = 1f;
    private float xCoOrdinate, yCoOrdinate;

    int lastQuestion;


    public DiagramQuestionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DiagramQuestionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DiagramQuestionFragment newInstance(Subsection param1, String param2) {
        DiagramQuestionFragment fragment = new DiagramQuestionFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = (Subsection) getArguments().getSerializable(ARG_PARAM1);
           mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_diagram_question, container, false);


        try {
            image = (ImageView) v.findViewById(R.id.imageView2);
            Picasso.get().load(mParam1.getImageurl()).into(image);
            pva = new PhotoViewAttacher(image);

            int totalQ = (int)mParam1.getQuestion().values().size();

            tab = (TableLayout) v.findViewById(R.id.table);

            TextView[] textArray = new TextView[totalQ + 2];
            EditText[] editTexts = new EditText[totalQ + 2];
            TableRow[] tr_head = new TableRow[totalQ + 2];

            tr_head[0] = new TableRow(this.getContext());
            tr_head[0].setId(0 + 1);
            tr_head[0].setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));

            // Here create the TextView dynamically
            Button but = new Button(this.getContext());
            but.setText("Options");
            but.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("View Info");
                    builder.setMessage("" + answerStringBuilder(mParam1.getSectionText()));
                    builder.setCancelable(false);
                    builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });

                    builder.show();
                }
            });
            tr_head[0].addView(but);

            tab.addView(tr_head[0], new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));

            lastQuestion = ((TestActivity)getActivity()).getLast();

            for (int i = 1; i < totalQ + 1; i++) {

                //Create the tablerows
                tr_head[i] = new TableRow(this.getContext());
                tr_head[i].setId(i);
                tr_head[i].setLayoutParams(new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT));

                // Here create the TextView dynamically
                TextView textView = new TextView(this.getContext());
                EditText editText = new EditText(this.getContext());



                editTexts[i] = editText;
                editTexts[i].setSingleLine(true);
                editTexts[i].setPadding(2, 5, 2, 5);
                editTexts[i].setWidth(500);
                textView.setText("  " + (i) + " ");
                textArray[i] = textView;
                textArray[i].setPadding(2, 5, 2, 5);
                tr_head[i].addView(textArray[i]);
                tr_head[i].addView(editTexts[i]);


                tab.addView(tr_head[i], new TableLayout.LayoutParams(
                        TableLayout.LayoutParams.MATCH_PARENT,
                        TableLayout.LayoutParams.WRAP_CONTENT));
                ((TestActivity)getActivity()).plusLast();

            }

            int y = totalQ + 1;

            tr_head[y] = new TableRow(this.getContext());
            tr_head[y].setId(y + 1);
            tr_head[y].setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));

            // Here create the TextView dynamically
            Button but2 = new Button(this.getContext());
            but2.setText("Save & Next");
            but2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //call next
                    List<String> answers = new ArrayList<String>();
                    for (int i = 1; i < totalQ + 1; i++){
                        answers.add(editTexts[i].getText().toString());
                    }

                    ((TestActivity)getActivity()).save(answers);
                }
            });
            tr_head[y].addView(but2);

            tab.addView(tr_head[y], new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));

        }catch (Exception ex){
            ex.getMessage();
        }
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public String answerStringBuilder (String str){
        String[] strArr = str.split("\\|");
        String built = new String();

        for(int i=0; i<strArr.length;i++)
        {
            built+=strArr[i]+"\n";
        }
        return built;
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
    private Bitmap decodeFromBase64ToBitmap(String encodedImage) {

        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);

        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        return decodedByte;

    }

}
