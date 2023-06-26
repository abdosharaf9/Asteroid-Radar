package com.abdosharaf.asteroidradar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(version = 1, entities = [AsteroidEntity::class])
abstract class AsteroidsDatabase : RoomDatabase() {

    abstract val dao: AsteroidsDao

    companion object {
        @Volatile
        private lateinit var instance: AsteroidsDatabase

        fun getInstance(context: Context): AsteroidsDatabase {
            synchronized(AsteroidsDatabase::class.java) {
                if (!::instance.isInitialized) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AsteroidsDatabase::class.java,
                        "asteroids"
                    ).build()
                }
            }
            return instance
        }
    }
}