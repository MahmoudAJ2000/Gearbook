package com.example.gearbook;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.math.BigDecimal;
import java.util.Date;

/*Gear class to represent each gear the user enters.
* each gear has a date,maker,description,price and an optional comment
* this class also keeps track of the sum of all gears' prices through the static variable "totalPrice"
* */
public class Gear {
    private Date date;
    private String maker;
    private String description;
    private Double price;
    private String comment;
    private static BigDecimal totalPrice = new BigDecimal("0.0");


    @RequiresApi(api = Build.VERSION_CODES.N)
    public Gear(Date date, String maker, String description, Double price) {
        this.date = date;
        this.maker = maker;
        this.description = description;
        this.price = price;
        this.comment = "N/A";
        this.totalPrice = this.getTotalPrice().add(BigDecimal.valueOf(price));//each time an instance of this class is created, totalPrice gets updated
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Gear(Date date, String maker, String description, Double price, String comment) {
        this.date = date;
        this.maker = maker;
        this.description = description;
        this.price = price;
        this.comment = comment;
        this.totalPrice = this.getTotalPrice().add(BigDecimal.valueOf(price));
    }

    public static BigDecimal getTotalPrice(){//returns the total price of all gears
        return Gear.totalPrice;
    }
    /*This method is used to update the totalPrice when a gear is edited or added*/
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void setTotalPrice(double sumPrice) {
        Gear.totalPrice = Gear.getTotalPrice().add(BigDecimal.valueOf(sumPrice));
    }
    /*setters and getters*/
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getMaker() {
        return maker;
    }
    public void setMaker(String maker) {
        this.maker = maker;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
}
