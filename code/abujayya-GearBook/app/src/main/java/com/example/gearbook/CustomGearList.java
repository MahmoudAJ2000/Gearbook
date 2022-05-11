package com.example.gearbook;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
/*
inherits from ArrayAdapter<>, its purpose is to display the gears in a custom ListView using the layout from content.xml
 */
public class CustomGearList extends ArrayAdapter<Gear> {
    private ArrayList<Gear> gears;
    private Context context;

    public CustomGearList(ArrayList<Gear> gears, Context context) {
        super(context, 0, gears);
        this.gears = gears;
        this.context = context;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.content,parent,false);
        }

        Gear gear = gears.get(position);

        TextView description = view.findViewById(R.id.descriptionText);
        TextView date = view.findViewById(R.id.dateText);
        TextView price = view.findViewById(R.id.priceText);

        /*Display each gear description, price and date in the custom listView*/
        description.setText(gear.getDescription());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(gear.getDate());//convert the date to the desired format
        date.setText(strDate);
        price.setText(gear.getPrice().toString());

        return view;
    }
}

