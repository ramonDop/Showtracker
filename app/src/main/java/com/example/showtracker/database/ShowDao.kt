package com.example.showtracker.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.showtracker.model.Show

@Dao
interface ShowDao {

    @Insert
    suspend fun insertShow(show: Show)

    @Query("SELECT * FROM Show")
    fun getAllShows(): LiveData<List<Show>?>

    @Update
    suspend fun updateShow(show: Show)

    @Delete
    suspend fun deleteShow(show: Show)
}