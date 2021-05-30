package com.example.fragmentsapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class NoteListFragment extends Fragment {

    DateManager dateManager = new DateManager();
    NoteAdapter.OnItemClickListener onItemClickListener;
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

        dataSource = NoteDataSourceImplementation.getInstance();
        initRecyclerView();

        setHasOptionsMenu(true);

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

        SwipeMenu swipeMenu = new SwipeMenu(requireContext(),recyclerView,200) {

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
                int position = dataSource.addNote(new Note("Added note " + dataSource.getSize(),
                        "This is added note",
                        dateManager.getDateNowString(),
                        false));
                adapter.notifyItemInserted(position);
                recyclerView.smoothScrollToPosition(position);
                return true;
            case R.id.action_clear:
                dataSource.clearNoteData();
                adapter.notifyDataSetChanged();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.card_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = adapter.getMenuPosition();
        switch (item.getItemId()) {
            case R.id.action_update:
                dataSource.updateNoteData(position, new Note("Edited note " + position,
                        "This is edited note",
                        dateManager.getDateNowString(),
                        false));
                adapter.notifyItemChanged(position);
                return true;
            case R.id.action_delete:
                dataSource.deleteNote(position);
                adapter.notifyItemRemoved(position);
                return true;
        }


        return super.onContextItemSelected(item);
    }

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NoteAdapter(dataSource, this);
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