package com.example.notes.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("notes")
data class Note(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "description") var description: String
)