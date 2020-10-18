package com.example.demo.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Helper {

    public static final DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    public static Date extractDateFromCSV(String date) throws ParseException {
        date=date.trim();
        date=date.split(" ")[0].concat("T").concat(date.split(" ")[1]).concat("Z");
        return format.parse(date);
    }
}
