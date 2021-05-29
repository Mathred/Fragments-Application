package com.example.fragmentsapplication;

public interface NoteDataSource {
    Note getNote(int position);

    int getSize();

    void deleteNote(int position);

    void updateNoteData(int position, Note note);

    int addNote(Note note);

    void clearNoteData();
}
