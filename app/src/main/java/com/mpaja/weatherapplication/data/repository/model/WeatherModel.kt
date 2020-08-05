package com.mpaja.weatherapplication.data.repository.model

data class WeatherModel (
    val weatherName: String,
    val cityname: String,
    val temperature: String,
    val pressure: String,
    val humidity: String,
    val maxTemp: String,
    val minTemp: String,
    val iconCode: String
)