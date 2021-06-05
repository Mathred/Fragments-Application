package com.example.fragmentsapplication.data;

public interface NoteDataSource {

    NoteDataSource init(NoteDataSourceResponse noteDataSourceResponse);

    Note getNote(int position);

    int getSize();

    void deleteNote(int position);

    void updateNoteData(int position, Note note);

    void addNote(Note note);

    void clearNoteData();
}
