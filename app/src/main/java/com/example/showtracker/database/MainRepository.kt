package com.example.showtracker.database

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.showtracker.model.Show

class MainRepository (context: Context) {

    private val showDao: ShowDao

    init {
        val database = MainRoomDatabase.getMainDatabase(context)
        showDao = database!!.showDao()
    }

    fun getAllShows() : LiveData<List<Show>?> {
        return showDao.getAllShows()
    }

    suspend fun updateShow(show: Show) {
        return showDao.updateShow(show)
    }

    suspend fun deleteShow(show: Show) {
        return showDao.deleteShow(show)
    }

    suspend fun insertShow(show: Show) {
        return showDao.insertShow(show)
    }
}