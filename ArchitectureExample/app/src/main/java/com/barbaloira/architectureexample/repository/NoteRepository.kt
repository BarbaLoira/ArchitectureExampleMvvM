package com.barbaloira.architectureexample.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.barbaloira.architectureexample.dao.NoteDao
import com.barbaloira.architectureexample.database.NoteDatabase
import com.barbaloira.architectureexample.entity.Note

//n faz parte da arch do android mas é boa pratica do design pattern
class NoteRepository {
    var noteDao: NoteDao

    var allNotes: LiveData<List<Note>>


    //quando singleton necessita de um context pasamos um application
    constructor(application: Application) {
        var database: NoteDatabase = NoteDatabase.getInstance(application)!!
        noteDao = database.noteDao()
        allNotes = noteDao.getAllNotes()
    }

    fun insert(note: Note) {
        InsertNoteAsyncTask(noteDao).execute(note)
    }

    fun update(note: Note) {
        UpdateNoteAsyncTask(noteDao).execute(note)
    }

    fun delete(note: Note) {
        DeleteNoteAsyncTask(noteDao).execute(note)
    }

    fun deleteAllNotes() {
        DeleteAllNoteAsyncTask(noteDao).execute()
    }

    fun getANotes(): LiveData<List<Note>> {
        return allNotes;
    }


    companion object {
        class InsertNoteAsyncTask : AsyncTask<Note, Void, Void> {
            var noteDao: NoteDao? = null

            constructor(noteDao: NoteDao) {
                this.noteDao = noteDao
            }

            override fun doInBackground(vararg params: Note?): Void? {
                noteDao?.insert(params.get(0))
                return null
            }

        }


        class UpdateNoteAsyncTask : AsyncTask<Note, Void, Void> {
            var noteDao: NoteDao? = null

            constructor(noteDao: NoteDao) {
                this.noteDao = noteDao
            }

            override fun doInBackground(vararg params: Note): Void? {
                noteDao?.update(params.get(0)!!)
                return null
            }

        }


        class DeleteNoteAsyncTask : AsyncTask<Note, Void, Void> {
            var noteDao: NoteDao? = null

            constructor(noteDao: NoteDao) {
                this.noteDao = noteDao
            }

            override fun doInBackground(vararg params: Note?): Void? {
                noteDao?.delete(params.get(0)!!)
                return null
            }

        }

        class DeleteAllNoteAsyncTask : AsyncTask<Void, Void, Void> {
            var noteDao: NoteDao? = null

            constructor(noteDao: NoteDao) {
                this.noteDao = noteDao
            }

            override fun doInBackground(vararg params: Void?): Void? {
                noteDao?.deleteAllNotes()
                return null
            }

        }

    }


}