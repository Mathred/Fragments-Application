package com.example.fragmentsapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    NoteDataSource dataSource;

    public NoteAdapter(NoteDataSource dataSource) {
        this.dataSource = dataSource;
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

        private TextView name;
        private TextView date;
        private CheckBox isFavorite;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_list_name);
            date = itemView.findViewById(R.id.item_list_date);
            isFavorite = itemView.findViewById(R.id.item_list_favorite);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO Implement creating new Fragment (Note details)
                }
            });
        }

        public void setData(Note note){
            name.setText(note.getName());
            date.setText(note.getDateCreated());
            if (note.isFavorite()) {
                isFavorite.setChecked(true);
            } else {
                isFavorite.setChecked(false);
            }
        }
    }
}
