package com.barbaloira.architectureexample.database

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.barbaloira.architectureexample.dao.NoteDao
import com.barbaloira.architectureexample.entity.Note

@Database(entities = arrayOf(Note::class), version = 1)
abstract class NoteDatabase : RoomDatabase() {


    abstract fun noteDao(): NoteDao

    //compation == static java
    companion object {
      private  var instance: NoteDatabase? = null

        fun getInstance(context: Context): NoteDatabase? {
            // sincronizacao evitar criar 2 instancias
            synchronized(this) {
                if (instance == null) {
                    instance =
                            Room.databaseBuilder(context.applicationContext, NoteDatabase::class.java, "note_database")
                                .fallbackToDestructiveMigration()
                                .addCallback(roomCallback)//para populacao dos dados na criacao do bd
                                .build()
                }
            }
            return instance
        }


        private val roomCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                PopulateDbAsyncTask(instance).execute()
            }
        }


        private class PopulateDbAsyncTask : AsyncTask<Void, Void, Void> {

            private var noteDao: NoteDao? = null

            constructor(db: NoteDatabase?) {
                noteDao = db?.noteDao()//bd retornando as operacoes que nela pode ser feita
            }

            override fun doInBackground(vararg params: Void?): Void? {
                noteDao!!.insert(Note("Title 1", "Discription 1", 1))
                noteDao!!.insert(Note("Title 2", "Discription 2", 2))
                noteDao!!.insert(Note("Title 3", "Discription 3", 3))
                return null
            }


        }


    }

}