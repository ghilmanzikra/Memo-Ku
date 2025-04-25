package com.example.memo_ku.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memo_ku.R;
import com.example.memo_ku.model.Note;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private final Context context;
    private final List<Note> noteList;

    public interface OnNoteClickListener {
        void onNoteClick(Note note);
    }

    public interface OnNoteLongClickListener {
        void onNoteLongClick(Note note);
    }

    private OnNoteClickListener clickListener;
    private OnNoteLongClickListener longClickListener;

    public void setOnNoteClickListener(OnNoteClickListener listener) {
        this.clickListener = listener;
    }

    public void setOnNoteLongClickListener(OnNoteLongClickListener listener) {
        this.longClickListener = listener;
    }

    public NoteAdapter(Context context, List<Note> noteList) {
        this.context = context;
        this.noteList = noteList;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = noteList.get(position);
        holder.tvTitle.setText(note.title);
        holder.tvContent.setText(note.content);

        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) clickListener.onNoteClick(note);
        });

        holder.itemView.setOnLongClickListener(v -> {
            if (longClickListener != null) longClickListener.onNoteLongClick(note);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvContent;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvContent = itemView.findViewById(R.id.tvContent);
        }
    }
}
