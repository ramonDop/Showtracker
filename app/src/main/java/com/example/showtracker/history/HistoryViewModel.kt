package com.example.showtracker.history


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.showtracker.database.HistoryRepository
import com.example.showtracker.model.Show
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryViewModel(application: Application) : AndroidViewModel(application) {

    private val historyRepository = HistoryRepository(application.applicationContext)
    private val mainScope = CoroutineScope(Dispatchers.Main)

    val allShows = historyRepository.getAllShows()

    fun deleteShow(show: Show) {
        mainScope.launch {
            historyRepository.deleteShow(show)
        }
    }
}