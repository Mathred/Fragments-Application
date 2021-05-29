package com.example.fragmentsapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

        dataSource = new NoteDataSourceImplementation().getDefaultNoteList();
        initRecyclerView();

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
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

    private void initRecyclerView() {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NoteAdapter(dataSource);
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