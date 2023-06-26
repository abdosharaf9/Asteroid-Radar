package com.abdosharaf.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.abdosharaf.asteroidradar.Asteroid
import com.abdosharaf.asteroidradar.PictureOfDay
import com.abdosharaf.asteroidradar.database.AsteroidsDatabase
import com.abdosharaf.asteroidradar.repository.AsteroidsRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MainViewModel(app: Application) : AndroidViewModel(app) {

    private val database = AsteroidsDatabase.getInstance(app)
    private val repo = AsteroidsRepository(database)

    val asteroids = repo.asteroids

    private val _picOfDay: MutableLiveData<PictureOfDay> = MutableLiveData()
    val picOfDay: LiveData<PictureOfDay>
        get() = _picOfDay

    private val _navigateToDetailFragment = MutableLiveData<Asteroid?>(null)
    val navigateToDetailFragment
        get() = _navigateToDetailFragment

    init {
        try {
            viewModelScope.launch {
                repo.refreshAsteroidsData()
                getPicOfDay()
            }
        } catch (e: Exception) {
            Log.e("TAG", "$e")
            Log.e("TAG", "${e.printStackTrace()}")
        }
    }

    private suspend fun getPicOfDay() {
        _picOfDay.value = repo.getPicOfTheDay()
    }

    fun startNavigating(item: Asteroid) {
        _navigateToDetailFragment.value = item
    }

    fun doneNavigating() {
        _navigateToDetailFragment.value = null
    }
}