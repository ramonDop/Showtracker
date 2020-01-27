package com.example.showtracker.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.showtracker.database.MainRepository
import com.example.showtracker.model.Show
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val mainRepository = MainRepository(application.applicationContext)
    private val mainScope = CoroutineScope(Dispatchers.Main)

    val allShows = mainRepository.getAllShows()

    fun deleteShow(show: Show) {
        mainScope.launch {
            mainRepository.deleteShow(show)
        }
    }
}