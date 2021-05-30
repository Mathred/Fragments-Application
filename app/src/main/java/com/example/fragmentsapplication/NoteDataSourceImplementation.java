package com.example.fragmentsapplication;

import java.util.ArrayList;
import java.util.List;

public class NoteDataSourceImplementation implements NoteDataSource {
    private static List<Note> noteListDataSource;
    private static final DateManager dateManager = new DateManager();
    private static NoteDataSourceImplementation instance;

    public NoteDataSourceImplementation(List<Note> noteList) {
        noteListDataSource = noteList;
    }

    public static NoteDataSourceImplementation getInstance() {
        if (instance == null) {
            createDefaultNoteList();
            instance = new NoteDataSourceImplementation(noteListDataSource);
        }
        return instance;
    }

    public static void createDefaultNoteList() {

        List<Note> defaultList = new ArrayList<>();
        defaultList.add(new Note("First", "This is first note", dateManager.getDateNowString(), true));
        defaultList.add(new Note("Second", "This is second note", dateManager.getDateNowString(), false));
        defaultList.add(new Note("Third", "This is third note", dateManager.getDateNowString(), false));
        defaultList.add(new Note("Forth", "This is forth note", dateManager.getDateNowString(), false));
        defaultList.add(new Note("Fifth", "This is fifth note", dateManager.getDateNowString(), false));
        defaultList.add(new Note("Sixth", "This is sixth note", dateManager.getDateNowString(), false));
        defaultList.add(new Note("Seven", "This is seventh note", dateManager.getDateNowString(), false));
        defaultList.add(new Note("Eight", "This is eighth note", dateManager.getDateNowString(), false));
        defaultList.add(new Note("Nine", "This is ninth note", dateManager.getDateNowString(), false));
        defaultList.add(new Note("Ten", "This is tenth note", dateManager.getDateNowString(), false));
        defaultList.add(new Note("Eleven", "This is eleventh note", dateManager.getDateNowString(), false));
        defaultList.add(new Note("Twelve", "This is twelfth note", dateManager.getDateNowString(), false));
        defaultList.add(new Note("Thirteen", "This is thirteenth note", dateManager.getDateNowString(), false));

        noteListDataSource = defaultList;

    }

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
     * @param note for addition
     * @return position of added element
     */
    @Override
    public int addNote(Note note) {
        noteListDataSource.add(note);
        return noteListDataSource.indexOf(note);
    }

    @Override
    public void clearNoteData() {
        noteListDataSource.clear();
    }


}
