package com.example.fragmentsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Note note = new Note();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createDefaultNotes();
    }

    private void createDefaultNotes() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date dateNow = new Date();

        note.addNote(new Note("First", "This is first note", formatter.format(dateNow)));
        dateNow = new Date();
        note.addNote(new Note("Second", "This is second note", formatter.format(dateNow)));
        dateNow = new Date();
        note.addNote(new Note("Third", "This is third note", formatter.format(dateNow)));

    }
}