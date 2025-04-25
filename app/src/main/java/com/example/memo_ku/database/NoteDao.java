package com.example.memo_ku.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.memo_ku.model.Note;

import java.util.List;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM notes")
    List<Note> getAllNotes();

    @Insert
    void insertNote(Note note);

    @Delete
    void deleteNote(Note note);
}
