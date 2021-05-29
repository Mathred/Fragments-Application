package com.example.fragmentsapplication;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.Calendar;

public class NoteDetailsFragment extends Fragment {

    protected static final String ARG_NOTE = "note";
    private final DateManager dateManager = new DateManager();
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

        EditText noteNameEditText = view.findViewById(R.id.note_name);
        noteNameEditText.setText(note.getName());

        TextView noteDateCreatedEditText = view.findViewById(R.id.note_date_created);
        noteDateCreatedEditText.setText(note.getDateCreated());

        Button button = view.findViewById(R.id.change_date_button);
        button.setOnClickListener(v -> {
            Calendar date = Calendar.getInstance();
            int mYear = date.get(Calendar.YEAR);
            int mMonth = date.get(Calendar.MONTH);
            int mDay = date.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dialog = new DatePickerDialog(requireContext(), (view1, year, month, dayOfMonth) -> {
                date.set(year, month, dayOfMonth);
                note.setDateCreated(dateManager.getFormatter().format(date.getTime()));
                noteDateCreatedEditText.setText(note.getDateCreated());
            }, mYear, mMonth, mDay);
            dialog.show();
        }
        );

        TextView noteDescriptionTextView = view.findViewById(R.id.note_description);
        noteDescriptionTextView.setText(note.getDescription());

        CheckBox isFavorite = view.findViewById(R.id.note_favorite);
        isFavorite.setChecked(note.isFavorite());

        isFavorite.setOnCheckedChangeListener((buttonView, isChecked) -> note.setFavorite(isChecked));

        return view;
    }

}

