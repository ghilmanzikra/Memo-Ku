package com.example.memo_ku.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class Note {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String title;
    public String content;

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
