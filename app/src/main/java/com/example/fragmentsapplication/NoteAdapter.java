package com.example.fragmentsapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import javax.sql.DataSource;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    NoteDataSource dataSource;
    private OnItemClickListener itemClickListener;
    private Fragment fragment;
    private int menuPosition;

    public NoteAdapter(NoteDataSource dataSource, Fragment fragment) {
        this.dataSource = dataSource;
        this.fragment = fragment;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public int getMenuPosition() {
        return menuPosition;
    }

    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.setData(dataSource.getNote(position));
    }

    @Override
    public int getItemCount() {
        return dataSource.getSize();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Note note;
        private TextView name;
        private TextView date;
        private CheckBox isFavorite;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_list_name);
            date = itemView.findViewById(R.id.item_list_date);
            isFavorite = itemView.findViewById(R.id.item_list_favorite);

            if (fragment != null) {
                fragment.registerForContextMenu(itemView);
            }


            isFavorite.setOnCheckedChangeListener((buttonView, isChecked) -> {
                note.setFavorite(isFavorite.isChecked());
            });

            itemView.setOnClickListener(v -> {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(v,getAdapterPosition());
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    menuPosition = getLayoutPosition();
                    v.showContextMenu();
                    return true;
                }
            });

        }

        public void setData(Note note){
            this.note = note;
            name.setText(note.getName());
            date.setText(note.getDateCreated());
            isFavorite.setChecked(note.isFavorite());
        }
    }

}
