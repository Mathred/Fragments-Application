package com.example.fragmentsapplication;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NoteDataSource {

    List<Note> list;

    private final String pattern = "dd-MM-yyyy";
    @SuppressLint("SimpleDateFormat")
    private final SimpleDateFormat formatter = new SimpleDateFormat(pattern);

    public Note getNote(int i) {
        return list.get(i);
    }

    public int getSize() {
        return list.size();
    }

    public List<Note> getDefaultNoteList() {
        Date dateNow = new Date();
        String dateNowString = formatter.format(dateNow);

        List<Note> list = new ArrayList<>();
        list.add(new Note("First", "This is first note", dateNowString, true));
        list.add(new Note("Second", "This is second note", dateNowString, false));
        list.add(new Note("Third", "This is third note", dateNowString, false));
        list.add(new Note("Forth", "This is forth note", dateNowString, false));
        list.add(new Note("Fifth", "This is fifth note", dateNowString, false));
        list.add(new Note("Sixth", "This is sixth note", dateNowString, false));

        list.add(new Note("First", "This is first note", dateNowString, false));
        list.add(new Note("Second", "This is second note", dateNowString, false));
        list.add(new Note("Third", "This is third note", dateNowString, false));
        list.add(new Note("Forth", "This is forth note", dateNowString, false));
        list.add(new Note("Fifth", "This is fifth note", dateNowString, false));
        list.add(new Note("Sixth", "This is sixth note", dateNowString, false));

        return list;
    }
}
