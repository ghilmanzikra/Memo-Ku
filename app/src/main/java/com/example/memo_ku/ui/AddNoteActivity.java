package com.example.memo_ku.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.memo_ku.R;
import com.example.memo_ku.database.NoteDatabase;
import com.example.memo_ku.model.Note;

public class AddNoteActivity extends AppCompatActivity {

    EditText etTitle, etContent;
    Button btnSave;
    NoteDatabase db;

    int noteId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        etTitle = findViewById(R.id.etTitle);
        etContent = findViewById(R.id.etContent);
        btnSave = findViewById(R.id.btnSave);

        db = NoteDatabase.getInstance(this);

        // Ambil data intent (kalau dari edit)
        noteId = getIntent().getIntExtra("note_id", -1);
        if (noteId != -1) {
            etTitle.setText(getIntent().getStringExtra("title"));
            etContent.setText(getIntent().getStringExtra("content"));
        }

        btnSave.setOnClickListener(v -> {
            String title = etTitle.getText().toString().trim();
            String content = etContent.getText().toString().trim();

            if (TextUtils.isEmpty(title) || TextUtils.isEmpty(content)) {
                Toast.makeText(this, "Harap isi semua kolom", Toast.LENGTH_SHORT).show();
                return;
            }

            if (noteId == -1) {
                db.noteDao().insertNote(new Note(title, content));
            } else {
                Note updated = new Note(title, content);
                updated.id = noteId;
                db.noteDao().deleteNote(new Note("", "")); // sederhana dulu
                db.noteDao().insertNote(updated); // bisa dioptimasi pakai @Update
            }

            Toast.makeText(this, "Catatan disimpan", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}
