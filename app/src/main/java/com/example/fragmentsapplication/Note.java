package com.example.fragmentsapplication;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Note implements Parcelable {

    private final String pattern = "dd-MM-yyyy";
    @SuppressLint("SimpleDateFormat")
    private final SimpleDateFormat formatter = new SimpleDateFormat(pattern);
    private String name;
    private String description;
    private String dateCreated;
    private List<Note> noteList = new ArrayList<>();



    public Note() {

    }

    public Note(String name, String description, String dateCreated) {
        this.name = name;
        this.description = description;
        this.dateCreated = dateCreated;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void addNote(Note note) {
        noteList.add(note);
    }

    public List<Note> getDefaultNoteList() {
        Date dateNow = new Date();
        String dateNowString = formatter.format(dateNow);

        List<Note> list = new ArrayList<>();
        list.add(new Note("First", "This is first note", dateNowString));
        list.add(new Note("Second", "This is second note", dateNowString));
        list.add(new Note("Third", "This is third note", dateNowString));
        list.add(new Note("Forth", "This is forth note", dateNowString));
        list.add(new Note("Fifth", "This is fifth note", dateNowString));
        list.add(new Note("Sixth", "This is sixth note", dateNowString));

        list.add(new Note("First", "This is first note", dateNowString));
        list.add(new Note("Second", "This is second note", dateNowString));
        list.add(new Note("Third", "This is third note", dateNowString));
        list.add(new Note("Forth", "This is forth note", dateNowString));
        list.add(new Note("Fifth", "This is fifth note", dateNowString));
        list.add(new Note("Sixth", "This is sixth note", dateNowString));

        return list;
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    protected Note(Parcel in) {
        name = in.readString();
        description = in.readString();
        dateCreated = in.readString();
        noteList = in.createTypedArrayList(Note.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(dateCreated);
        dest.writeTypedList(noteList);
    }
}
