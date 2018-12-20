package com.barbaloira.architectureexample.gui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.barbaloira.architectureexample.R
import com.barbaloira.architectureexample.util.Constants
import kotlinx.android.synthetic.main.activity_add_note.*

class AddNoteActivity : AppCompatActivity() {
    lateinit var editTextTitle: EditText
    lateinit var editTextDescription: EditText
    lateinit var numberPickerPriority: RatingBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)


        editTextTitle = inpt_title
        editTextDescription = inpt_descrp

        numberPickerPriority = nmbr_priority



        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_close)
        title = "Add Note"

    }


    private fun saveNote() {
        var title: String = editTextTitle.text.toString()
        var description: String = editTextDescription.text.toString()
        var prior: Int = numberPickerPriority.rating.toInt()

        if (title.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Plase insert a title and description", Toast.LENGTH_SHORT)
            editTextTitle.error = "*"
            editTextDescription.error = "*"
            return
        }

        var data: Intent = Intent()
        data.putExtra(Constants.EXTRA_TITLE, title)
        data.putExtra(Constants.EXTRA_DESCRIPTION, description)
        data.putExtra(Constants.EXTRA_PRIORITY, prior)

        setResult(Activity.RESULT_OK, data)

        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        var menuInflater: MenuInflater = menuInflater
        menuInflater.inflate(R.menu.add_note_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {


        return when (item!!.itemId) {
            R.id.save_note -> {
                saveNote()
                return true
            }
            else -> super.onOptionsItemSelected(item)

        }

    }

}
