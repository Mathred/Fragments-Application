package com.example.fragmentsapplication.ui;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.fragmentsapplication.DateManager;
import com.example.fragmentsapplication.data.Note;
import com.example.fragmentsapplication.data.NoteDataSourceImplementation;
import com.example.fragmentsapplication.R;

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

        initNameEditText(view);
        initDatePickerButton(view);
        initDescriptionEditText(view);
        initIsFavoriteCheckbox(view);

        return view;
    }

    private void initDescriptionEditText(View view) {
        EditText noteDescriptionEditText = view.findViewById(R.id.note_description);
        noteDescriptionEditText.setText(note.getDescription());
        noteDescriptionEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                note.setDescription(String.valueOf(s));
            }
        });
    }

    private void initNameEditText(View view) {
        EditText noteNameEditText = view.findViewById(R.id.note_name);
        noteNameEditText.setText(note.getName());
        noteNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                note.setName(String.valueOf(s));
            }
        });
    }

    private void initIsFavoriteCheckbox(View view) {
        CheckBox isFavorite = view.findViewById(R.id.note_favorite);
        isFavorite.setChecked(note.isFavorite());
        isFavorite.setOnCheckedChangeListener((buttonView, isChecked) -> note.setFavorite(isChecked));
    }

    private void initDatePickerButton(View view) {

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
    }

}

