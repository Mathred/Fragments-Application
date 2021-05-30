package com.example.fragmentsapplication;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateManager {

    private final String pattern = "dd-MM-yyyy";
    @SuppressLint("SimpleDateFormat")
    private final SimpleDateFormat formatter = new SimpleDateFormat(pattern);

    public String getDateNowString() {
        Date dateNow = new Date();
        return formatter.format(dateNow);
    }

    public SimpleDateFormat getFormatter() {
        return formatter;
    }
}
