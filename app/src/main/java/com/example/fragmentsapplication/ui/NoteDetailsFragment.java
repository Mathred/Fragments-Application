package com.example.fragmentsapplication.ui;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;

import com.example.fragmentsapplication.R;
import com.example.fragmentsapplication.data.Note;
import com.example.fragmentsapplication.data.NoteDataSource;
import com.example.fragmentsapplication.data.NoteDataSourceFirebaseImpl;
import com.example.fragmentsapplication.data.NoteDataSourceResponse;

public class NoteDetailsFragment extends Fragment {

    protected static final String ARG_NOTE = "note";
    private NoteDataSource dataSource;
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
        }
        dataSource = new NoteDataSourceFirebaseImpl().init(noteDataSource -> {

        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_note_details, container, false);

        initEditText(R.id.note_name, view);
        initEditText(R.id.note_description, view);
        initIsFavoriteCheckbox(view);

        return view;
    }

    private void initEditText(@IdRes int editTextId, View view) {
        EditText editText = view.findViewById(editTextId);
        switch (editTextId) {
            case R.id.note_name:
                editText.setText(note.getName());
                break;
            case R.id.note_description:
                editText.setText(note.getDescription());
        }
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                switch (editTextId) {
                    case R.id.note_name:
                        note.setName(String.valueOf(s));
                        break;
                    case R.id.note_description:
                        note.setDescription(String.valueOf(s));
                }
                dataSource.updateNoteData(note);
            }
        });
    }

    private void initIsFavoriteCheckbox(View view) {
        CheckBox isFavorite = view.findViewById(R.id.note_favorite);
        isFavorite.setChecked(note.isFavorite());
        isFavorite.setOnCheckedChangeListener((buttonView, isChecked) -> {
            note.setFavorite(isChecked);
            dataSource.updateNoteData(note);
        });
    }

}

