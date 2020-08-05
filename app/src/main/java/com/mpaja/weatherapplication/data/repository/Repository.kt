package com.mpaja.weatherapplication.data.repository

import com.mpaja.weatherapplication.data.repository.model.CityModel
import com.mpaja.weatherapplication.data.repository.model.ForecastModel
import com.mpaja.weatherapplication.data.repository.model.WeatherModel
import io.reactivex.Completable
import io.reactivex.Single

interface Repository {

    fun getCities(cityName: String): Single<List<CityModel>>
    fun getWeatherForCity(cityId: Int): Single<WeatherModel>
    fun getCitiesHistory(): Single<List<CityModel>>
    fun saveCityToDatabase(cityModel: CityModel): Completable
    fun getForecast(cityId: Int): Single<List<ForecastModel>>
}