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

    public Note() {

    }

    public Note(String name, String description, String dateCreated) {
        this.name = name;
        this.description = description;
        this.dateCreated = dateCreated;
    }

    protected Note(Parcel in) {
        name = in.readString();
        description = in.readString();
        dateCreated = in.readString();
        noteList = in.createTypedArrayList(Note.CREATOR);
    }

    public List<Note> getNoteList() {
        return noteList;
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

    public List<Note> getDefaultNoteList() {
        Date dateNow = new Date();

        List<Note> list = new ArrayList<>();
        list.add(new Note("First", "This is first note", formatter.format(dateNow)));
        dateNow = new Date();
        list.add(new Note("Second", "This is second note", formatter.format(dateNow)));
        dateNow = new Date();
        list.add(new Note("Third", "This is third note", formatter.format(dateNow)));
        return list;
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
