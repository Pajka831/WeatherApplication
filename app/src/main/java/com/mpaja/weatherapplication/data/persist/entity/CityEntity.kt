package com.mpaja.weatherapplication.data.persist.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city")
data class CityEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val cityName: String
)