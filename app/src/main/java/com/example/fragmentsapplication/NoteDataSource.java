package com.example.fragmentsapplication;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface NoteDataSource {
    Note getNote(int position);
    int getSize();


}
