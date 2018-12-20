package com.barbaloira.architectureexample

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.barbaloira.architectureexample.adapter.NoteAdapter
import com.barbaloira.architectureexample.entity.Note
import com.barbaloira.architectureexample.gui.AddNoteActivity
import com.barbaloira.architectureexample.util.Constants
import com.barbaloira.architectureexample.viewModel.NoteViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var noteViewModel: NoteViewModel
    private final lateinit var adapter: NoteAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createRecycleView()

        configViewModel()

        var floatActionButton: FloatingActionButton = findViewById(R.id.button_add_note)
        floatActionButton.setOnClickListener {

            var intent = Intent(this, AddNoteActivity::class.java)
            startActivityForResult(intent, Constants.ADD_NOTE)
        }


    }


    fun configViewModel() {

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)

        noteViewModel!!.getAllNotes().observe(this,
            Observer<List<Note>> {
                adapter.addNotes(it)
                Toast.makeText(this, "update note_livedata", Toast.LENGTH_SHORT).show()
            })
    }

    private fun createRecycleView() {
        var recycleView: RecyclerView = findViewById(R.id.recycle_view)
        recycleView.layoutManager = LinearLayoutManager(this)
        recycleView.setHasFixedSize(true) //boa pratica

        adapter = NoteAdapter()
        recycleView.adapter = adapter

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.ADD_NOTE && resultCode == Activity.RESULT_OK) {

            var title = data!!.getStringExtra(Constants.EXTRA_TITLE)
            var description = data!!.getStringExtra(Constants.EXTRA_DESCRIPTION)
            var priority = data!!.getIntExtra(Constants.EXTRA_PRIORITY, 1)


            var note: Note = Note(title, description, priority)
            noteViewModel.insert(note)


            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Sry 404", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onResume() {
        super.onResume()
        swiperDeleteItens()
    }

    private fun swiperDeleteItens() {
        val simpleItemTouchCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition

                    if (direction == ItemTouchHelper.LEFT) {
                        noteViewModel.delete(adapter.getNoteAt(position))
                        //  Toast.makeText(this, "Note deleted", Toast.LENGTH_SHORT)

                    }
                }
            }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recycle_view)
    }
}
