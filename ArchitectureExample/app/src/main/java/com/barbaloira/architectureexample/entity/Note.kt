package com.barbaloira.architectureexample.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class Note(
      var title: String,

      var description: String,

      var priority: Int

) {
    @PrimaryKey(autoGenerate = true) // caso queira mudar o id @columnInfo ( name  = "")
    var id: Int = 0
}