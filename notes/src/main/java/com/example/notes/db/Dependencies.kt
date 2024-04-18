package com.example.notes.db

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.notes.repository.NoteRepository

object Dependencies {
    private lateinit var applicationContext: Context

    fun init(context: Context) {
        applicationContext = context
    }

    private val appDatabase: NoteDatabase by lazy {
        Room.databaseBuilder(applicationContext, NoteDatabase::class.java, "notes.db")
            .addMigrations(MIGRATION_1_2)
            .build()
    }

    val noteRepository: NoteRepository by lazy { NoteRepository(appDatabase.getNotesDao()) }
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE notes ADD COLUMN recent_changes INTEGER NOT NULL DEFAULT 0")
    }
}