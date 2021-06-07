package com.example.fragmentsapplication.ui;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fragmentsapplication.R;
import com.example.fragmentsapplication.data.Note;
import com.example.fragmentsapplication.data.NoteAdapter;
import com.example.fragmentsapplication.data.NoteDataSource;
import com.example.fragmentsapplication.data.NoteDataSourceFirebaseImpl;
import com.example.fragmentsapplication.swipeMenu.SwipeController;
import com.example.fragmentsapplication.swipeMenu.SwipeControllerActions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;

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

        SwipeController swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onLeftClicked(int position) {
                NoteDetailsFragment noteDetailsFragment = NoteDetailsFragment.newInstance(dataSource.getNote(position));
                assert getFragmentManager() != null;
                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, noteDetailsFragment)
                        .addToBackStack(null)
                        .commit();
            }

            @Override
            public void onRightClicked(int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                builder.setTitle(R.string.deleteConfirmationAlertDialogTitle)
                        .setMessage("Delete note " + dataSource.getNote(position).getName() + "?")
                        .setPositiveButton(R.string.delete, (dialog, which) -> {
                            dataSource.deleteNote(position);
                            adapter.notifyItemRemoved(position);
                        })
                        .setNeutralButton(R.string.cancel,(dialog, which) -> {})
                        .show();
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeController);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });

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

                AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                builder.setTitle(R.string.deleteConfirmationAlertDialogTitle)
                        .setMessage(R.string.clearAll)
                        .setPositiveButton(R.string.delete, (dialog, which) -> {
                            dataSource.clearNoteData();
                            adapter.notifyDataSetChanged();
                        })
                        .setNeutralButton(R.string.cancel, (dialog, which) -> {
                            dialog.dismiss();
                        })
                        .show();
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