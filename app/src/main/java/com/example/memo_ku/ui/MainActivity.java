package com.example.memo_ku.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.memo_ku.R;
import com.example.memo_ku.adapter.NoteAdapter;
import com.example.memo_ku.database.NoteDatabase;
import com.example.memo_ku.model.Note;
import com.example.memo_ku.utils.SharedPrefHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import com.example.memo_ku.auth.LoginActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton fabAdd;
    NoteDatabase db;
    List<Note> notes;
    NoteAdapter adapter;
    SharedPrefHelper pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = new SharedPrefHelper(this);

        if (!pref.isLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_main);

        db = NoteDatabase.getInstance(this);
        notes = db.noteDao().getAllNotes();

        recyclerView = findViewById(R.id.recyclerView);
        fabAdd = findViewById(R.id.fabAdd);

        adapter = new NoteAdapter(this, notes);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter.setOnNoteClickListener(note -> {
            Intent intent = new Intent(this, AddNoteActivity.class);
            intent.putExtra("note_id", note.id);
            intent.putExtra("title", note.title);
            intent.putExtra("content", note.content);
            startActivity(intent);
        });

        adapter.setOnNoteLongClickListener(note -> {
            db.noteDao().deleteNote(note);
            Toast.makeText(this, "Catatan dihapus", Toast.LENGTH_SHORT).show();
            refreshNotes();
        });

        fabAdd.setOnClickListener(v -> {
            startActivity(new Intent(this, AddNoteActivity.class));
        });

        // Menggunakan setOnItemSelectedListener yang benar
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_home:
                    return true;
                case R.id.menu_profile:
                    Toast.makeText(this, "Fitur profil belum tersedia", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.menu_logout:
                    pref.clearSession();
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(this, LoginActivity.class));
                    finish();
                    return true;
                default:
                    return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshNotes();
    }

    private void refreshNotes() {
        notes.clear();
        notes.addAll(db.noteDao().getAllNotes());
        adapter.notifyDataSetChanged();
    }
}
