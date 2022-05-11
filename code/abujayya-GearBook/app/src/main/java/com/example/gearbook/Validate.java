package com.example.gearbook;

import android.widget.EditText;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

//the purpose of this class is to validate the user's enteries and handle any invalid inputs
public abstract class Validate {

    private static boolean isTextual(String text){//validates if a string is alphanumerical, with a few special characters
        if (!text.matches("[0-9a-zA-Z-._\\s/]+") && !text.replaceAll("\\s","").matches("")){
            return false;
        }
        return true;
    }
    /*source:https://www.baeldung.com/java-string-valid-date*/
    private static boolean isValidDateFormat(String date) {//check if the date entered is in the correct format and returns a boolean
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");//set the date format pattern
        sdf.setLenient(false);
        try {//try to convert the string to a date with the specified pattern
            sdf.parse(date);
            return true;
        } catch (ParseException e) {//if it fails, then the date has an invalid format
            return false;
        }
    }

    private static boolean isValidDateChars(String date){//checks if the date entered contains any invalid characters
        if (!date.matches("[0-9-]+")){
            return false;
        }
        return true;
    }
    //source: https://www.geeksforgeeks.org/double-parsedouble-method-in-java-with-examples/
    private static boolean isValidPrice(String price){//checks if the price entered is a valid price
        try{//try to convert the entered value to a double
            double d=Double.parseDouble(price);
            return true;
        }
        catch (NumberFormatException e){//if it can't be converted, then it's invalid
            return false;
        }

    }
    /*this function takes all the user's enteries and checks if they are valid. I fany of those entries are invalid an errors message will be displayed in the EditText boxes*/
    public static boolean ValidateAll(String maker, String description, String comment, String price,String date, EditText addMaker, EditText addDescription, EditText addPrice,EditText addDate,EditText addComment){
        boolean validInput = true;
        if (!Validate.isTextual(maker) || maker.replaceAll("\\s","").matches("")){//if maker is invalid
            validInput = false;
            if(!Validate.isTextual(maker)){//if maker has invalid characters display this message
                addMaker.setError("Invalid character(s) found, valid characters are A-Z a-z 0-9 . _ -");// https://www.c-sharpcorner.com/UploadFile/1e5156/validation/
            }
            else{//if the user did not enter a maker display this message
                addMaker.setError("This field can not be empty!");
            }
        }
        if ( description.replaceAll("\\s","").matches("")){
            validInput = false;
            if(!Validate.isTextual(description)){//if description has invalid characters display this message
                addDescription.setError("Invalid character(s) found, valid characters are A-Z a-z 0-9 . _ -");
            }
            else{//if the user did not enter a description display this message
                addDescription.setError("This field can not be empty!");
            }
        }
        if (!Validate.isTextual(comment)){//if comment has invalid characters display this message
            validInput = false;
            addComment.setError("Invalid character(s) found, valid characters are A-Z a-z 0-9 . _ -");
        }
        if (!Validate.isValidPrice(price) || price.replaceAll("\\s","").matches("")){
            validInput = false;
            if(!Validate.isValidPrice(price)  && !price.replaceAll("\\s","").matches("")){//if price has invalid characters display this message
                addPrice.setError("Invalid price entered!");
            }
            else{//if the user did not enter a price display this message
                addPrice.setError("This field can not be empty!");
            }
        }
        if (!Validate.isValidDateFormat(date)||!Validate.isValidDateChars(date)||date.replaceAll("\\s","").matches("")){
            validInput = false;
            if(!Validate.isValidDateFormat(date) && !date.replaceAll("\\s","").matches("")){//if the date eneterd is in an invalid format, display this message
                addDate.setError("Invalid date format entered, the correct format is yyyy-MM-dd");
            }
            if(!Validate.isValidDateChars(date) && !date.replaceAll("\\s","").matches("")){//if date has invalid characters display this message
                addDate.setError("Invalid character(s) found, valid characters are 0-9 and (-)");
            }
            else{//if the user did not enter a date display this message
                addDate.setError("This field can not be empty!");
            }
        }
        return validInput;//return true if all entries are valid, false otherwise
    }

}
