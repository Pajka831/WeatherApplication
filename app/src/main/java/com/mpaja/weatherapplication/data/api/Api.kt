package com.mpaja.weatherapplication.data.api

import com.mpaja.weatherapplication.data.api.model.CitiesResponse
import com.mpaja.weatherapplication.data.api.model.ForecastResponse
import com.mpaja.weatherapplication.data.api.model.WeatherResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("find")
    fun getListOfCities(@Query("q") cityName: String): Single<CitiesResponse>

    @GET("weather")
    fun getWeather(@Query("id") id: Int): Single<WeatherResponse>

    @GET("forecast")
    fun getForecast(@Query("id") id: Int): Single<ForecastResponse>

}