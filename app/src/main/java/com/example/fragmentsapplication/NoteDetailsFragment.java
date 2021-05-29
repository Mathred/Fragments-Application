package com.example.fragmentsapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class NoteDetailsFragment extends Fragment {

    protected static final String ARG_NOTE = "note";
    private Note note;

    public NoteDetailsFragment() {
    }

    public static NoteDetailsFragment newInstance(Note note) {
        NoteDetailsFragment fragment = new NoteDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            note = getArguments().getParcelable(ARG_NOTE);
        } else {
            note = NoteDataSourceImplementation.getInstance().getNote(0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_note_details, container, false);

        TextView noteNameTextView = view.findViewById(R.id.note_name);
        noteNameTextView.setText(note.getName());

        TextView noteDateCreatedTextView = view.findViewById(R.id.note_date_created);
        noteDateCreatedTextView.setText(note.getDateCreated());

        TextView noteDescriptionTextView = view.findViewById(R.id.note_description);
        noteDescriptionTextView.setText(note.getDescription());

        CheckBox isFavorite = view.findViewById(R.id.note_favorite);
        isFavorite.setChecked(note.isFavorite());
        return view;
    }

}

