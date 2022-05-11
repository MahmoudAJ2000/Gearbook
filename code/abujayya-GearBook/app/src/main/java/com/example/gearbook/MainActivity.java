package com.example.gearbook;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
//implements addGearFragment.OnFragmentInteractionListener
public class MainActivity extends AppCompatActivity implements addGearFragment.OnFragmentInteractionListener{

    ListView gearList;
    ArrayAdapter<Gear> gearAdapter;
    ArrayList<Gear> gearDataList;
    TextView totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        totalPrice = findViewById(R.id.totalPrice);
        totalPrice.setText("0");
        gearList = findViewById(R.id.gearList);
        gearDataList = new ArrayList<>();
        gearAdapter = new CustomGearList(gearDataList, this);

        /*Button to add new gear to the gear list*/
        FloatingActionButton addGearButton = findViewById(R.id.addGear);
        View.OnClickListener addListener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new addGearFragment().show(getSupportFragmentManager(), "ADD_GEAR");//initialize addGearFragment to display the "Add gear" window for the user
            }
        };
        addGearButton.setOnClickListener(addListener);

        /*Functionality to view the details of an item in the list when the user clicks on it*/
        AdapterView.OnItemClickListener listClick = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Gear gear = (Gear) gearList.getItemAtPosition( i );//get the gear item at the position the user clicks on
                new infoGearFragment(gear,gearAdapter,totalPrice).show(getSupportFragmentManager(), "INFO_GEAR");//initialize infoGearFragment to display more info about the gear with additional options

            }
        };
        gearList.setOnItemClickListener(listClick);
        gearList.setAdapter(gearAdapter);
    }
    /*Override the onOkPressed method to add a new Gear item to the Gear List when the user clicks ok in the Add gear Fragment*/
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onOkPressed(Date date, String maker, String description, Double price, String comment) {
        Gear newGear;
        if (!comment.replaceAll("\\s","").matches(""))//if the user enters a comment
            newGear = new Gear(date, maker, description, price, comment);//initialize a new gear with a comment
        else
            newGear = new Gear(date, maker, description, price);//initialize a new gear without a comment
        gearAdapter.add(newGear);//add the new gear to the list
        totalPrice.setText(newGear.getTotalPrice().toString());//update the total price after adding a new gear item
        }
    }
