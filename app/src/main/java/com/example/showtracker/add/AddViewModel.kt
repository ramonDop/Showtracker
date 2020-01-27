package com.example.showtracker.add

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.showtracker.database.MainRepository
import com.example.showtracker.model.Show
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddViewModel(application: Application) : AndroidViewModel(application) {

    private val showRepository = MainRepository(application.applicationContext)
    private val mainScope = CoroutineScope(Dispatchers.Main)

    fun insertShow(show: Show) {
        mainScope.launch {
            showRepository.insertShow(show)
        }
    }

}