package com.example.fragmentsapplication;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Note implements Parcelable {


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
    private String name;
    private String description;
    private String dateCreated;
    private boolean isFavorite;
    private List<Note> noteList = new ArrayList<>();

    public Note(String name, String description, String dateCreated, boolean isFavorite) {
        this.name = name;
        this.description = description;
        this.dateCreated = dateCreated;
        this.isFavorite = isFavorite;
    }

    public Note() {

    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    protected Note(Parcel in) {
        name = in.readString();
        description = in.readString();
        dateCreated = in.readString();
        noteList = in.createTypedArrayList(Note.CREATOR);
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
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
