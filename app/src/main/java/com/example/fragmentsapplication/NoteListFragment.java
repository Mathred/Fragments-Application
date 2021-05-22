package com.example.fragmentsapplication;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class NoteListFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        setHasOptionsMenu(true);
        initPopup(view);
        return view;
    }

    private void initPopup(View view) {
        TextView textView = view.findViewById(R.id.textview_fragment_main);
        textView.setOnClickListener(v -> {
            Activity activity = requireActivity();
            PopupMenu popupMenu = new PopupMenu(activity, v);
            activity.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());
            Menu menu = popupMenu.getMenu();
            menu.findItem(R.id.item2_popup).setVisible(false);
            menu.add(0,123456,12,R.string.new_menu_item_added);
            popupMenu.setOnMenuItemClickListener(item -> {
                int id = item.getItemId();
                switch (id) {
                    case R.id.item1_popup:
                        Toast.makeText(getContext(),"Chosen popup item 1",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.item2_popup:
                        Toast.makeText(getContext(),"Chosen popup item 2",Toast.LENGTH_SHORT).show();
                        return true;
                    case 123456:
                        Toast.makeText(getContext(),"Chosen new popup item added",Toast.LENGTH_SHORT).show();
                        return true;
                }
                return true;
            });
            popupMenu.show();
        });
    }



}