package com.example.fragmentsapplication;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

public class NoteListFragment extends Fragment {

    public static final String CURRENT_NOTE = "CurrentNote";
    private boolean isLandscape;
    private Note currentNote;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        if (savedInstanceState != null) {
            currentNote = savedInstanceState.getParcelable(CURRENT_NOTE);
        } else {
            currentNote = new Note().getDefaultNoteList().get(0);
        }

        if (isLandscape) {
            showLandscapeNoteDetails(currentNote);
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
        Note note = new Note();

        List<Note> noteList = note.getDefaultNoteList();
        for (Note note1 : noteList) {

            TextView textView = new TextView(getContext());
            textView.setText(note1.getName());
            textView.setTextSize(20);
            linearLayout.addView(textView);

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentNote = note1;
                    showNoteDetails(note1);
                }
            });
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(CURRENT_NOTE, currentNote);
        super.onSaveInstanceState(outState);
    }


    private void showNoteDetails(Note note) {
        if (isLandscape) {
            showLandscapeNoteDetails(note);
        } else {
            showPortraitNoteDetails(note);
        }
    }

    private void showLandscapeNoteDetails(Note note) {
        NoteDetailsFragment noteDetailsFragment = NoteDetailsFragment.newInstance(note);

        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.note_details, noteDetailsFragment);

        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

    private void showPortraitNoteDetails(Note note) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), NoteDetailsActivity.class);
        intent.putExtra(NoteDetailsFragment.ARG_NOTE, note);
        startActivity(intent);
    }
}