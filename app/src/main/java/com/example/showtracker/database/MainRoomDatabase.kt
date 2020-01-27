package com.example.showtracker.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.showtracker.model.Show

@Database(entities = [Show::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MainRoomDatabase : RoomDatabase() {

    abstract fun showDao(): ShowDao

    companion object {
        private const val DATABASE_NAME = "SHOW_DATABASE"

        @Volatile
        private var INSTANCE: MainRoomDatabase? = null

        fun getMainDatabase(context: Context): MainRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(MainRoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            MainRoomDatabase::class.java, DATABASE_NAME
                        )
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return INSTANCE
        }
    }
}