package com.example.cz_trivia.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Student::class], version = 1)
abstract class StudentDatabase: RoomDatabase() {

    abstract val studentDAO:StudentDAO

    companion object{
        @Volatile
        var INSTANCE: StudentDatabase? = null
        private val LOCK=Any()
        operator fun invoke(context: Context):StudentDatabase =
            INSTANCE?: synchronized(LOCK){
                INSTANCE ?: databaseBuilder(context).also {
                    INSTANCE = it
                }
            }

        fun databaseBuilder(context: Context):StudentDatabase{
            return Room.databaseBuilder(
                context.applicationContext,
                StudentDatabase::class.java,
                "student_database"
            ).build()
        }
    }


}