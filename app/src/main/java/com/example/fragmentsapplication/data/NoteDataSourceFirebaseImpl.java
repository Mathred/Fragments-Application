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
import java.util.List;
import java.util.Map;

public class NoteDataSourceFirebaseImpl implements NoteDataSource {

    public static final String NOTES_COLLECTION = "notes";
    public static final String TAG = "[NoteDataSourceFirebaseImpl]";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private CollectionReference collection = db.collection(NOTES_COLLECTION);

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
    public void updateNoteData(int position, Note note) {
        collection.document(note.getId()).set(NoteDataMapping.toDocument(note));
    }

    @Override
    public void addNote(Note note) {
        collection.add(NoteDataMapping.toDocument(note)).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                note.setId(documentReference.getId());
            }
        });
    }

    @Override
    public void clearNoteData() {
        for (Note note : noteList) {
            collection.document(note.getId()).delete();
        }

        noteList = new ArrayList<>();
    }
}
