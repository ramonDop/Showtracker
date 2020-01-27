package com.example.showtracker.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.showtracker.model.Show

@Database(entities = [Show::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class HistoryRoomDatabase : RoomDatabase() {

    abstract fun showDao(): ShowDao

    companion object {
        private const val DATABASE_NAME = "HISTORY_DATABASE"

        @Volatile
        private var INSTANCE: HistoryRoomDatabase? = null

        fun getHistoryDatabase(context: Context): HistoryRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(HistoryRoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            HistoryRoomDatabase::class.java, DATABASE_NAME
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