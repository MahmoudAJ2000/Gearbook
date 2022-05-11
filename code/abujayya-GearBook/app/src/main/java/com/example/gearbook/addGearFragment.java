package com.example.gearbook;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*this fragment creates a dialog box for the user to enter the details of the new gear to be entered*/
public class addGearFragment extends DialogFragment {
    private EditText addDescription;
    private EditText addMaker;
    private EditText addDate;
    private EditText addPrice;
    private EditText addComment;
    private TextView counterD;
    private TextView counterM;
    private TextView counterC;
    private OnFragmentInteractionListener listener;

    public addGearFragment() {
    }

    /*define an interface implemented in MainActivity*/
    public interface OnFragmentInteractionListener {
        void onOkPressed(Date date, String maker, String description, Double price, String comment);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener){
            listener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }


    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        /*get the add_gear_fragment layout and its objects*/
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_gear_fragment, null);
        addDescription = view.findViewById(R.id.descriptionInputText);
        addMaker = view.findViewById(R.id.makerInputText);
        addDate = view.findViewById(R.id.dateInputText);
        addPrice = view.findViewById(R.id.priceInputText);
        addComment = view.findViewById(R.id.commentInputText);
        counterD = view.findViewById(R.id.counterDesc);
        counterM = view.findViewById(R.id.counterMaker);
        counterC = view.findViewById(R.id.counterComment);
        /*create a dialog object from the layout and set user interface buttons*/
        final AlertDialog builder = new AlertDialog.Builder(getContext())
                .setView(view)
                .setTitle("Add Gear")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK",null)
                .show();
        /*override the dialog's positiveButton's onClick method to prevent the dialog from dismissing in the case where the user enters invalid input
        * source:https://www.youtube.com/watch?v=Kz9TkDY2sP8
        * */
        Button positiveButton = builder.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String description = addDescription.getText().toString();
                String maker = addMaker.getText().toString();
                String date = addDate.getText().toString();
                String price = addPrice.getText().toString();
                String comment = addComment.getText().toString();
                boolean proceed = Validate.ValidateAll( maker,  description,  comment,  price, date,  addMaker,  addDescription,  addPrice, addDate, addComment);
                if (proceed){
                    Date newDate;
                    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        newDate = sdf.parse(date);
                        listener.onOkPressed(newDate, maker, description, Double.valueOf(price), comment);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    dismiss();
                }
            }
        });
        /*
        Authors:Depinder Bharti,Tom Macdonald
        Date: Sep 12th, 2012
        Title: Live Character count for EditText
        License:open-source
        https://stackoverflow.com/questions/3013791/live-character-count-for-edittext
        */
        final TextWatcher watcherD = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                counterD.setText(String.valueOf(s.length())); //This sets a textview to the current length
            }
            public void afterTextChanged(Editable s) {
            }
        };
        addDescription.addTextChangedListener(watcherD);//add the watcherD listener to the EditText object
        /*do the same for the maker and the comment EditTexts*/
        final TextWatcher watcherM = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                counterM.setText(String.valueOf(s.length()));
            }
            public void afterTextChanged(Editable s) {
            }
        };
        addMaker.addTextChangedListener(watcherM);
        final TextWatcher watcherC = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                counterC.setText(String.valueOf(s.length()));
            }
            public void afterTextChanged(Editable s) {
            }
        };
        addComment.addTextChangedListener(watcherC);

        return builder;

    }


}
