package com.mpaja.weatherapplication.data.repository.model

data class ForecastModel (
    val dtMillis: Int,
    val tempMax: String,
    val tempMin: String,
    val iconCode: String
)