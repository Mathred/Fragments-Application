package com.example.fragmentsapplication;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NoteDataSourceImplementation implements NoteDataSource{
    List<Note> noteListDataSource;

    private final String pattern = "dd-MM-yyyy";
    @SuppressLint("SimpleDateFormat")
    private final SimpleDateFormat formatter = new SimpleDateFormat(pattern);

    public Note getNote(int i) {
        return noteListDataSource.get(i);
    }

    public int getSize() {
        return noteListDataSource.size();
    }

    @Override
    public void deleteNote(int position) {
        noteListDataSource.remove(position);
    }

    @Override
    public void updateNoteData(int position, Note note) {
        noteListDataSource.set(position, note);
    }

    /**
     *
     * @param note for addition
     * @return position of added element
     */
    @Override
    public int addNote(Note note) {
        noteListDataSource.add(note);
        return noteListDataSource.indexOf(note);
    }

    @Override
    public void clearNoteData(int position) {
        noteListDataSource.clear();
    }

    public NoteDataSourceImplementation getDefaultNoteList() {
        Date dateNow = new Date();
        String dateNowString = formatter.format(dateNow);

        List<Note> defaultList = new ArrayList<>();
        defaultList.add(new Note("First", "This is first note", dateNowString, true));
        defaultList.add(new Note("Second", "This is second note", dateNowString, false));
        defaultList.add(new Note("Third", "This is third note", dateNowString, false));
        defaultList.add(new Note("Forth", "This is forth note", dateNowString, false));
        defaultList.add(new Note("Fifth", "This is fifth note", dateNowString, false));
        defaultList.add(new Note("Sixth", "This is sixth note", dateNowString, false));

        defaultList.add(new Note("First", "This is first note", dateNowString, false));
        defaultList.add(new Note("Second", "This is second note", dateNowString, false));
        defaultList.add(new Note("Third", "This is third note", dateNowString, false));
        defaultList.add(new Note("Forth", "This is forth note", dateNowString, false));
        defaultList.add(new Note("Fifth", "This is fifth note", dateNowString, false));
        defaultList.add(new Note("Sixth", "This is sixth note", dateNowString, false));

        if (this.noteListDataSource != null) {
            this.noteListDataSource.removeAll(this.noteListDataSource);
        }
        this.noteListDataSource = defaultList;

        return this;
    }
}
