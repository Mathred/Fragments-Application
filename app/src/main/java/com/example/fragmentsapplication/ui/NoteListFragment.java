package com.example.fragmentsapplication.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fragmentsapplication.R;
import com.example.fragmentsapplication.data.Note;
import com.example.fragmentsapplication.data.NoteAdapter;
import com.example.fragmentsapplication.data.NoteDataSource;
import com.example.fragmentsapplication.data.NoteDataSourceFirebaseImpl;
import com.example.fragmentsapplication.data.NoteDataSourceResponse;
import com.example.fragmentsapplication.swipeMenu.SwipeMenu;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;
import java.util.List;

public class NoteListFragment extends Fragment {

    private NoteAdapter adapter;
    private RecyclerView recyclerView;
    private NoteDataSource dataSource;

    public static NoteListFragment newInstance() {
        return new NoteListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = view.findViewById(R.id.recyclerView_noteList);

        initRecyclerView();
        setHasOptionsMenu(true);

        dataSource = new NoteDataSourceFirebaseImpl().init(noteDataSource -> adapter.notifyDataSetChanged());
        adapter.setDataSource(dataSource);


        FloatingActionButton floatingActionButton = view.findViewById(R.id.floating_action_add);
        floatingActionButton.setOnClickListener(v -> {
            NewNoteFragment newNoteFragment = NewNoteFragment.newInstance();
            assert getFragmentManager() != null;
            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, newNoteFragment)
                    .addToBackStack(null)
                    .commit();
        });

        SwipeMenu swipeMenu = new SwipeMenu(requireContext(), recyclerView, 200) {

            @Override
            public void instantiateMyButton(RecyclerView.ViewHolder viewHolder, List<SwipeMenu.MyButton> buffer) {
                buffer.add(new MyButton(getContext(), "Delete", 40, Color.RED, position -> {
                    dataSource.deleteNote(position);
                    adapter.notifyItemRemoved(position);
                }));
                buffer.add(new MyButton(getContext(), "Edit", 40, Color.BLUE, position -> {
                    NoteDetailsFragment noteDetailsFragment = NoteDetailsFragment.newInstance(dataSource.getNote(position));
                    assert getFragmentManager() != null;
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, noteDetailsFragment)
                            .addToBackStack(null)
                            .commit();
                }));
            }
        };

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.cards_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                dataSource.addNote(new Note("Added note " + dataSource.getSize(),
                        "This is added note",
                        new Date(),
                        false));
                adapter.notifyItemInserted(dataSource.getSize());
                recyclerView.scrollToPosition(dataSource.getSize()-1);
                return true;
            case R.id.action_clear:
                dataSource.clearNoteData();
                adapter.notifyDataSetChanged();
                return true;
            case R.id.action_resetToDefaultList:
                dataSource.resetNoteList();
                adapter.notifyDataSetChanged();
        }

        return super.onOptionsItemSelected(item);
    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NoteAdapter(this);
        adapter.setDataSource(new NoteDataSourceFirebaseImpl().init(noteDataSource -> {
        }));
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