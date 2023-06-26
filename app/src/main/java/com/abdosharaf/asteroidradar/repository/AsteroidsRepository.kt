package com.abdosharaf.asteroidradar.repository

import com.abdosharaf.asteroidradar.Asteroid
import com.abdosharaf.asteroidradar.Constants
import com.abdosharaf.asteroidradar.api.AsteroidAPIService
import com.abdosharaf.asteroidradar.api.parseAsteroidsJsonResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AsteroidsRepository {

    // TODO: Get the Asteroids list from the database!!

    suspend fun getAsteroids(): List<Asteroid> {
        val responseStr = AsteroidAPIService.ApiService.getAsteroids("", "", Constants.API_KEY)
        val responseJson = JSONObject(responseStr)

        return parseAsteroidsJsonResult(responseJson)
    }

    suspend fun getPicOfTheDay() = AsteroidAPIService.ApiService.getPictureOfDay(Constants.API_KEY)

    suspend fun refreshAsteroidsData() {
        withContext(Dispatchers.IO) {
            // TODO: Get data from the API and insert it into the database!!
        }
    }
}