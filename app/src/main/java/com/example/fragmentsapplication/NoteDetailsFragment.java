package com.example.fragmentsapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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
            note = new Note().getDefaultNoteList().get(0);
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

        DatePicker datePicker = view.findViewById(R.id.date_picker);
        Button button = view.findViewById(R.id.set_date);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String date;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                Calendar calendar = Calendar.getInstance();
                calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth());
                date = simpleDateFormat.format(calendar.getTime());
                note.setDateCreated(date);
                noteDateCreatedTextView.setText(note.getDateCreated());
            }
        });


        TextView noteDescriptionTextView = view.findViewById(R.id.note_description);
        noteDescriptionTextView.setText(note.getDescription());


        return view;
    }

}