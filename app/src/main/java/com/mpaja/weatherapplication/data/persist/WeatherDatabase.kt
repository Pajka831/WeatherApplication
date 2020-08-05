package com.mpaja.weatherapplication.data.persist

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mpaja.weatherapplication.data.persist.dao.CityDao
import com.mpaja.weatherapplication.data.persist.entity.CityEntity

@Database(entities = [CityEntity::class], version = 1, exportSchema = false)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun cityDao(): CityDao

    companion object {
        const val DB_NAME = "weather_database"
    }
}