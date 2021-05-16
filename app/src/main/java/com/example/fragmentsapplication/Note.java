package com.example.fragmentsapplication;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

public class Note {
    private String name;
    private String description;
    private DateFormat dateCreated;
    private List<Note> noteList = new ArrayList<>();

    public List<Note> getNoteList() {
        return noteList;
    }

    public String getName() {
        return name;
    }

    public Note() {

    }

    public Note(String first, String this_is_first_note, String format) {
    }

    public Note(String name, String description, DateFormat dateCreated) {
        this.name = name;
        this.description = description;
        this.dateCreated = dateCreated;
    }

    public void addNote(Note note) {
        noteList.add(note);
    }
}
