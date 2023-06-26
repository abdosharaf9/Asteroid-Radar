package com.abdosharaf.asteroidradar.application

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.abdosharaf.asteroidradar.database.AsteroidsDatabase
import com.abdosharaf.asteroidradar.repository.AsteroidsRepository
import retrofit2.HttpException

class AsteroidsWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "AsteroidsWorker"
    }

    override suspend fun doWork(): Result {
        val database = AsteroidsDatabase.getInstance(applicationContext)
        val repo = AsteroidsRepository(database)

        return try {
            repo.refreshAsteroidsData()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }


}