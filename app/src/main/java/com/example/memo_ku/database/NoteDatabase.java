package com.example.memo_ku.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.memo_ku.model.Note;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase INSTANCE;

    public abstract NoteDao noteDao();

    public static synchronized NoteDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NoteDatabase.class, "memo_ku_db")
                    .allowMainThreadQueries() // Boleh untuk tugas/UTS, production jangan pakai ini ya
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
