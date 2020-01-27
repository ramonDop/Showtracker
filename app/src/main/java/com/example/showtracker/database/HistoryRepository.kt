package com.example.showtracker.database

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.showtracker.model.Show

class HistoryRepository (context: Context) {

    private val showDao: ShowDao

    init {
        val database = HistoryRoomDatabase.getHistoryDatabase(context)
        showDao = database!!.showDao()
    }

    fun getAllShows() : LiveData<List<Show>?> {
        return showDao.getAllShows()
    }

    suspend fun deleteShow(show: Show) {
        return showDao.deleteShow(show)
    }

    suspend fun insertShow(show: Show) {
        return showDao.insertShow(show)
    }
}