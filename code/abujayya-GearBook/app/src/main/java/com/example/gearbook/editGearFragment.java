package com.example.gearbook;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/*this is a child fragment of infoGearFragment. It displays a dialog box for the user to edit a gear's info*/
public class editGearFragment extends DialogFragment {
    private EditText editDescription;
    private EditText editMaker;
    private EditText editDate;
    private EditText editPrice;
    private EditText editComment;
    private Gear gear;
    private TextView totalPrice;
    private TextView counterD;
    private TextView counterM;
    private TextView counterC;
    private Dialog parent;


    public editGearFragment(Gear gear, TextView totalPrice,Dialog parent) {
        this.gear = gear;
        this.totalPrice = totalPrice;
        this.parent=parent;
    }

    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        /*get the layout and its objects*/
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_gear_fragment, null);
        editDescription = view.findViewById(R.id.descriptionInputText);
        editMaker = view.findViewById(R.id.makerInputText);
        editDate = view.findViewById(R.id.dateInputText);
        editPrice = view.findViewById(R.id.priceInputText);
        editComment = view.findViewById(R.id.commentInputText);
        counterD = view.findViewById(R.id.counterDesc);
        counterM = view.findViewById(R.id.counterMaker);
        counterC = view.findViewById(R.id.counterComment);
        /*set the EditText boxes' text to the current's gear info*/
        editDescription.setText(gear.getDescription());
        editMaker.setText(gear.getMaker());
        editComment.setText(gear.getComment());
        final Double oldPrice = gear.getPrice();
        editPrice.setText(oldPrice.toString());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(gear.getDate());
        editDate.setText(strDate);
        /*create the dialog box wth user interface box*/
        AlertDialog builder = new AlertDialog.Builder(getContext())
                .setView(view)
                .setTitle("Edit Gear")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("OK",null).show();
        /*override the dialog's positiveButton's onClick method to prevent the dialog from dismissing in the case where the user enters invalid input
         * source:https://www.youtube.com/watch?v=Kz9TkDY2sP8
         * */
        Button positiveButton = builder.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
                                              @RequiresApi(api = Build.VERSION_CODES.N)
                                              @Override
                                              public void onClick(View view) {
                                                  /*get the entered details from the text boxes*/
                                                  String description = editDescription.getText().toString();
                                                  String maker = editMaker.getText().toString();
                                                  String date = editDate.getText().toString();
                                                  String price = editPrice.getText().toString();
                                                  String comment = editComment.getText().toString();
                                                  /*validate the users' enteries and handle any invalid entries*/
                                                  boolean proceed = Validate.ValidateAll(maker, description, comment, price, date, editMaker, editDescription, editPrice, editDate, editComment);
                                                  if (proceed) {//if all entries are valid, set the gear's attribute values to the new entered values
                                                      gear.setDescription(description);
                                                      gear.setMaker(maker);
                                                      try {
                                                          gear.setDate(new SimpleDateFormat("yyyy/MM/dd").parse(date)); //https://www.javatpoint.com/java-string-to-date
                                                      } catch (ParseException e) {
                                                          e.printStackTrace();
                                                      }
                                                      gear.setPrice(Double.valueOf(price));
                                                      gear.setComment(comment);
                                                      double newPrice = Double.parseDouble(price);//convert from string to doubel (https://www.javatpoint.com/java-string-to-double)
                                                      double priceDiff = newPrice - oldPrice.doubleValue();//get the difference between the old price and the new price
                                                      gear.setTotalPrice(priceDiff);
                                                      totalPrice.setText(gear.getTotalPrice().toString());//update the total price
                                                      parent.dismiss();//dismiss the parent fragment after successfully editing a gear
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
                counterD.setText(String.valueOf(s.length()));
            }

            public void afterTextChanged(Editable s) {
            }
        };
        editDescription.addTextChangedListener(watcherD);
        final TextWatcher watcherM = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                counterM.setText(String.valueOf(s.length()));
            }
            public void afterTextChanged(Editable s) {
            }
        };
        editMaker.addTextChangedListener(watcherM);
        final TextWatcher watcherC = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                counterC.setText(String.valueOf(s.length()));
            }
            public void afterTextChanged(Editable s) {
            }
        };
        editComment.addTextChangedListener(watcherC);
        return builder;
    }
}






