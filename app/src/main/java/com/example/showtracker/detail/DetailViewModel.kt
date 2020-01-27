package com.example.showtracker.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.showtracker.database.MainRepository
import com.example.showtracker.model.Show
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private val mainRepository = MainRepository(application.applicationContext)
    private val mainScope = CoroutineScope(Dispatchers.Main)

    fun updateShow(show: Show) {
        mainScope.launch {
            mainRepository.updateShow(show)
        }
    }

    fun deleteShow(show: Show) {
        mainScope.launch {
            mainRepository.deleteShow(show)
        }
    }
}