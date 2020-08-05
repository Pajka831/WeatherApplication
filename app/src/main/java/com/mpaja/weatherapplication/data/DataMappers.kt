package com.mpaja.weatherapplication.data

import com.mpaja.weatherapplication.data.api.model.CitiesResponse
import com.mpaja.weatherapplication.data.api.model.ForecastResponse
import com.mpaja.weatherapplication.data.api.model.WeatherResponse
import com.mpaja.weatherapplication.data.persist.entity.CityEntity
import com.mpaja.weatherapplication.data.repository.model.CityModel
import com.mpaja.weatherapplication.data.repository.model.ForecastModel
import com.mpaja.weatherapplication.data.repository.model.WeatherModel
import com.mpaja.weatherapplication.utils.convertKelvinToCelsiusInt

fun mapCityResponseToCityModel(cityDataResponse: CitiesResponse.CityData) =
    CityModel(
        id = cityDataResponse.id,
        cityName = cityDataResponse.name,
        countryCode = cityDataResponse.sys.country
    )

fun mapWeatherResponseToWeatherModel(weatherResponse: WeatherResponse) =
    WeatherModel(
        weatherName = weatherResponse.weather[0].main,
        temperature = convertKelvinToCelsiusInt(weatherResponse.main.temp).toString(),
        pressure = weatherResponse.main.pressure.toString(),
        humidity = weatherResponse.main.humidity.toString(),
        maxTemp = convertKelvinToCelsiusInt(weatherResponse.main.tempMax).toString(),
        minTemp = convertKelvinToCelsiusInt(weatherResponse.main.tempMin).toString(),
        iconCode = weatherResponse.weather[0].icon,
        cityname = weatherResponse.name
    )

fun mapCityModelToCityEntity(cityModel: CityModel) =
    CityEntity(
        cityName = cityModel.cityName
    )

fun mapCityEntityToCityModel(cityEntity: CityEntity) =
    CityModel(
        cityName = cityEntity.cityName,
        id = null,
        countryCode = null
    )

fun mapForecastResponseToForecastModel(forecastResponse: ForecastResponse.CityData) =
    ForecastModel(
        dtMillis = forecastResponse.dt,
        tempMax = convertKelvinToCelsiusInt(forecastResponse.main.tempMax).toString(),
        tempMin = convertKelvinToCelsiusInt(forecastResponse.main.tempMin).toString(),
        iconCode = forecastResponse.weather[0].icon
    )
