package com.mpaja.weatherapplication.data.repository

import android.util.Log
import com.mpaja.weatherapplication.data.*
import com.mpaja.weatherapplication.data.api.Api
import com.mpaja.weatherapplication.data.api.model.ForecastResponse
import com.mpaja.weatherapplication.data.persist.WeatherDatabase
import com.mpaja.weatherapplication.data.persist.dao.CityDao
import com.mpaja.weatherapplication.data.repository.model.CityModel
import com.mpaja.weatherapplication.data.repository.model.ForecastModel
import com.mpaja.weatherapplication.data.repository.model.WeatherModel
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RepositoryImpl @Inject constructor(
    private val api: Api,
    private val cityDao: CityDao
) : Repository {

    override fun getCities(cityName: String): Single<List<CityModel>> =
        api.getListOfCities(cityName).map { cityResponse ->
            cityResponse.list.map {
                mapCityResponseToCityModel(it)
            }
        }

    override fun getWeatherForCity(cityId: Int): Single<WeatherModel> =
        api.getWeather(cityId).map { weatherResponse ->
            mapWeatherResponseToWeatherModel(weatherResponse)
        }

    override fun getCitiesHistory(): Single<List<CityModel>> =
        cityDao.getAllCities().map { cityEntity ->
            cityEntity.map {
                mapCityEntityToCityModel(it)
            }
        }

    override fun saveCityToDatabase(cityModel: CityModel) =
        cityDao.insertCity(mapCityModelToCityEntity(cityModel))

    override fun getForecast(cityId: Int): Single<List<ForecastModel>> =
        api.getForecast(cityId).map { forecastResponse ->
            forecastResponse.list.map {
                mapForecastResponseToForecastModel(it)
            }
        }
}