package com.example.fragmentsapplication.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.fragmentsapplication.DateManager;
import com.example.fragmentsapplication.data.Note;
import com.example.fragmentsapplication.data.NoteDataSource;
import com.example.fragmentsapplication.data.NoteDataSourceFirebaseImpl;
import com.example.fragmentsapplication.R;
import com.example.fragmentsapplication.data.NoteDataSourceResponse;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewNoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewNoteFragment extends Fragment {

    DateManager dateManager = new DateManager();
    private Note note = new Note();
    private NoteDataSource dataSource;

    public NewNoteFragment() {}

    public static NewNoteFragment newInstance() {
        NewNoteFragment fragment = new NewNoteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_note, container, false);

        dataSource = new NoteDataSourceFirebaseImpl().init(new NoteDataSourceResponse() {
            @Override
            public void initialized(NoteDataSource noteDataSource) {
            }
        });

        initView(view);
        return view;
    }

    private void initView(View view) {

        EditText noteNameEditText = view.findViewById(R.id.new_note_name);
        EditText noteDescriptionEditText = view.findViewById(R.id.new_note_description);
        CheckBox isFavorite = view.findViewById(R.id.new_note_favorite);
        isFavorite.setChecked(false);
        initSaveButton(view,
                noteNameEditText,
                noteDescriptionEditText,
                isFavorite);
        initDiscardButton(view);

    }

    @NonNull
    private TextView initDatePicker(View view) {
        TextView noteDateCreatedEditText = view.findViewById(R.id.new_note_date_created);
        noteDateCreatedEditText.setText(dateManager.getDateNowString());

        return noteDateCreatedEditText;
    }

    private void initSaveButton(View view, EditText noteNameEditText, EditText noteDescriptionEditText, CheckBox isFavorite) {
        Button saveButton = view.findViewById(R.id.action_save_new_note);
        saveButton.setOnClickListener(v -> {
            note.setName(String.valueOf(noteNameEditText.getText()));
            note.setFavorite(isFavorite.isChecked());
            note.setDescription(String.valueOf(noteDescriptionEditText.getText()));
            dataSource.addNote(note);
            requireActivity().getSupportFragmentManager().popBackStack();
        });
    }

    private void initDiscardButton(View view) {
        Button discardButton = view.findViewById(R.id.action_discard_new_note);
        discardButton.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());
    }

}