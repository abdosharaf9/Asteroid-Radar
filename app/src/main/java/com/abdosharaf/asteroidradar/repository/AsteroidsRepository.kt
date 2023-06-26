package com.abdosharaf.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.abdosharaf.asteroidradar.Asteroid
import com.abdosharaf.asteroidradar.Constants
import com.abdosharaf.asteroidradar.api.AsteroidAPIService
import com.abdosharaf.asteroidradar.api.parseAsteroidsJsonResult
import com.abdosharaf.asteroidradar.database.AsteroidsDatabase
import com.abdosharaf.asteroidradar.database.toAsteroidsList
import com.abdosharaf.asteroidradar.toAsteroidEntities
import org.json.JSONObject

class AsteroidsRepository(private val database: AsteroidsDatabase) {

    val asteroids: LiveData<List<Asteroid>> = database.dao.getAll().map {
        it.toAsteroidsList()
    }

    private suspend fun getAsteroids(): List<Asteroid> {
        val responseStr = AsteroidAPIService.ApiService.getAsteroids("", "", Constants.API_KEY)
        val responseJson = JSONObject(responseStr)

        return parseAsteroidsJsonResult(responseJson)
    }

    suspend fun getPicOfTheDay() = AsteroidAPIService.ApiService.getPictureOfDay(Constants.API_KEY)

    suspend fun refreshAsteroidsData() {
        val asteroids = getAsteroids()
        database.dao.insertAll(asteroids.toAsteroidEntities())
    }
}