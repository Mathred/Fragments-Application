package com.example.fragmentsapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


public class NoteListFragment extends Fragment {

    Note note = new Note();

    public NoteListFragment() {
    }


    public static NoteListFragment newInstance() {
        NoteListFragment fragment = new NoteListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initNoteList(view);
    }

    private void initNoteList(View view) {
        LinearLayout linearLayout = (LinearLayout) view;

        for (Note eachNote : note.getNoteList()) {
            TextView textView = new TextView(getContext());
            textView.setText(eachNote.getName());
            textView.setTextSize(20);
            linearLayout.addView(textView);
        }

    }
}