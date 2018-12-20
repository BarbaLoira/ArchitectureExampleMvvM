package com.barbaloira.architectureexample.viewModel

import android.app.Application
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.barbaloira.architectureexample.entity.Note
import com.barbaloira.architectureexample.repository.NoteRepository

/**
 *Com o androidViewModel vc pode usar o Application, não é recomendado passar context para o viewModel  pois se vc ex
 * girar a tela vc vai manter referencia de uma tela destruida
 */

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: NoteRepository
    private var allNotes: LiveData<List<Note>>

    init {

        repository = NoteRepository(application)
        allNotes = repository.getANotes()
    }

    fun insert(note: Note) {
        repository.insert(note)
    }


    fun update(note: Note) {
        repository.update(note)
    }


    fun delete(note: Note) {
        repository.delete(note)
    }


    fun deleteAllNotes(note: Note) {
        repository.deleteAllNotes()
    }


    fun getAllNotes(): LiveData<List<Note>> {
        return allNotes
    }


}