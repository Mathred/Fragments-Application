package com.example.fragmentsapplication;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class NoteListFragment extends Fragment {

    NoteAdapter adapter;
    NoteAdapter.OnItemClickListener onItemClickListener;

    public static NoteListFragment newInstance() {
        return new NoteListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_noteList);

        NoteDataSource dataSource = new NoteDataSourceImplementation().getDefaultNoteList();
        initRecyclerView(recyclerView, dataSource);

        return view;
    }




    private void initRecyclerView(RecyclerView recyclerView, NoteDataSource dataSource) {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        final NoteAdapter adapter = new NoteAdapter(dataSource);
        recyclerView.setAdapter(adapter);

        adapter.SetOnItemClickListener((view, position) -> {
            NoteDetailsFragment noteDetailsFragment = NoteDetailsFragment.newInstance(dataSource.getNote(position));
            assert getFragmentManager() != null;
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, noteDetailsFragment)
                    .addToBackStack(null)
                    .commit();
        });

    }

}