package com.example.fragmentsapplication.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

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
    private String id;
    private String name;
    private String description;
    private Date dateCreated;
    private boolean isFavorite;

    public Note(String name, String description, Date dateCreated, boolean isFavorite) {
        this.name = name;
        this.description = description;
        this.dateCreated = dateCreated;
        this.isFavorite = isFavorite;
    }

    public Note() {

    }

    protected Note(Parcel in) {
        name = in.readString();
        description = in.readString();
        dateCreated = new Date(in.readLong());
        isFavorite = in.readByte() != 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeLong(dateCreated.getTime());
        dest.writeByte((byte) (isFavorite ? 1 : 0));
    }
}
