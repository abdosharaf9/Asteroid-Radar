package com.abdosharaf.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdosharaf.asteroidradar.Asteroid
import com.abdosharaf.asteroidradar.PictureOfDay
import com.abdosharaf.asteroidradar.repository.AsteroidsRepository
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val repo = AsteroidsRepository()

    private val _asteroids: MutableLiveData<List<Asteroid>> = MutableLiveData()
    val asteroids: LiveData<List<Asteroid>>
        get() = _asteroids

    private val _picOfDay: MutableLiveData<PictureOfDay> = MutableLiveData()
    val picOfDay: LiveData<PictureOfDay>
        get() = _picOfDay

    init {
        getAsteroids()
        getPicOfDay()
    }

    private fun getAsteroids() {
        viewModelScope.launch {
            _asteroids.value = repo.getAsteroids()
        }
    }

    private fun getPicOfDay() {
        viewModelScope.launch {
            _picOfDay.value = repo.getPicOfTheDay()
        }
    }
}