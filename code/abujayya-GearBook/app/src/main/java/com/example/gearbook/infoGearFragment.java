package com.example.gearbook;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class infoGearFragment extends DialogFragment {
    //private TextView descriptionText;
    private TextView makerText;
    private TextView dateText;
    private TextView priceText;
    private TextView commentText;
    private Gear gear;
    private ArrayAdapter<Gear> gearAdapter;
    private FloatingActionButton deleteGear;
    private FloatingActionButton editGear;
    private TextView totalPrice;


    public infoGearFragment(Gear gear,ArrayAdapter<Gear> gearAdapter, TextView totalPrice) {
        this.gear = gear;
        this.gearAdapter = gearAdapter;
        this.totalPrice = totalPrice;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        /*get the layout for the dialog box*/
        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.info_gear_fragment, null);
        makerText = view.findViewById(R.id.makerInfo);
        dateText = view.findViewById(R.id.dateInfo);
        priceText = view.findViewById(R.id.priceInfo);
        commentText = view.findViewById(R.id.commentInfo);
        deleteGear = view.findViewById(R.id.deleteGear);
        editGear = view.findViewById(R.id.editGear);
        /*display the gears' info in text views*/
        makerText.setText(gear.getMaker());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(gear.getDate());
        dateText.setText(strDate);
        priceText.setText(gear.getPrice().toString());
        commentText.setText(gear.getComment());
        /*create the dialog box*/
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final Dialog d = builder.setView(view)
                .setTitle(gear.getDescription()).create();
        //https://stackoverflow.com/questions/2115758/how-do-i-display-an-alert-dialog-on-android
        deleteGear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new AlertDialog.Builder(builder.getContext())
                        .setTitle("Delete Gear")
                        .setMessage("Are you sure you want to delete this gear?")
                        // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            public void onClick(DialogInterface dialog, int which) {
                                gearAdapter.remove(gear);
                                double priceDiff = (-1)*gear.getPrice();
                                gear.setTotalPrice(priceDiff);
                                totalPrice.setText(gear.getTotalPrice().toString());
                                dismiss();
                            }
                        })
                        // A null listener allows the button to dismiss the dialog and take no further action.
                        .setNegativeButton(android.R.string.no, null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
        editGear.setOnClickListener(new View.OnClickListener() {//when the edit button is clicked, an instance of the childe fragment editGearFragment is created
            public void onClick(View v) {
                new editGearFragment(gear,totalPrice,d).show(getChildFragmentManager(), "EDIT_GEAR");
            }
        });
        return d;
}}
