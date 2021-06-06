package com.example.fragmentsapplication.data;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class NoteDataSourceFirebaseImpl implements NoteDataSource {

    public static final String NOTES_COLLECTION = "notes";
    public static final String TAG = "[NoteDataSourceFirebaseImpl]";
    private static final List<Note> DEFAULT_NOTE_LIST = new ArrayList<Note>() {{
        add(new Note("First", "This is first note", new Date(), true));
        add(new Note("Second", "This is second note", new Date(), false));
        add(new Note("Third", "This is third note", new Date(), false));
        add(new Note("Forth", "This is forth note", new Date(), false));
        add(new Note("Fifth", "This is fifth note", new Date(), false));
        add(new Note("Sixth", "This is sixth note", new Date(), false));
        add(new Note("Seven", "This is seventh note", new Date(), false));
        add(new Note("Eight", "This is eighth note", new Date(), false));
        add(new Note("Nine", "This is ninth note", new Date(), false));
        add(new Note("Ten", "This is tenth note", new Date(), false));
        add(new Note("Eleven", "This is eleventh note", new Date(), false));
        add(new Note("Twelve", "This is twelfth note", new Date(), false));
        add(new Note("Thirteen", "This is thirteenth note", new Date(), false));
    }};
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference collection = db.collection(NOTES_COLLECTION);
    private List<Note> noteList = new ArrayList<>();

    @Override
    public NoteDataSource init(NoteDataSourceResponse noteDataSourceResponse) {
        collection.orderBy(NoteDataMapping.Fields.DATE_CREATED, Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("LongLogTag")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            noteList.clear();

                            for (QueryDocumentSnapshot document : task.getResult()) {

                                Map<String, Object> doc = document.getData();
                                Note note = NoteDataMapping.toNoteData(document.getId(), doc);
                                noteList.add(note);
                            }

                            Log.d(TAG, "isSuccessful");
                            noteDataSourceResponse.initialized(NoteDataSourceFirebaseImpl.this);

                        } else {
                            Log.d(TAG, "notSuccessful");

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "onFailure", e);
            }
        });
        return this;
    }

    @Override
    public Note getNote(int position) {
        return noteList.get(position);
    }

    @Override
    public int getSize() {
        if (noteList == null) {
            return 0;
        }
        return noteList.size();
    }

    @Override
    public void deleteNote(int position) {
        collection.document(getNote(position).getId()).delete();
        noteList.remove(position);
    }

    @Override
    public void updateNoteData(Note note) {
        collection.document(note.getId()).set(NoteDataMapping.toDocument(note));
    }

    @Override
    public void addNote(Note note) {
        collection.add(NoteDataMapping.toDocument(note)).addOnSuccessListener(documentReference -> note.setId(documentReference.getId()));
        noteList.add(note);
    }

    @Override
    public void clearNoteData() {
        for (Note note : noteList) {
            collection.document(note.getId()).delete();
        }
        noteList = new ArrayList<>();
    }

    @Override
    public void resetNoteList() {
        clearNoteData();

        for (Note note : DEFAULT_NOTE_LIST) {
            addNote(note);
        }
    }
}

