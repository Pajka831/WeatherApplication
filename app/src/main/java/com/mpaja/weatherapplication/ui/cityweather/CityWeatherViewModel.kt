package com.mpaja.weatherapplication.ui.cityweather

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mpaja.weatherapplication.R
import com.mpaja.weatherapplication.data.repository.Repository
import com.mpaja.weatherapplication.utils.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CityWeatherViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    var compositeDisposable = CompositeDisposable()

    private val _currentWeatherUiState = MutableLiveData<CurrentWeatherUiState>()
    val currentWeatherUiState: LiveData<CurrentWeatherUiState>
        get() = _currentWeatherUiState

    private val _forecastUiState = MutableLiveData<ForecastUiState>()
    val forecastUiState: LiveData<ForecastUiState>
        get() = _forecastUiState

    private val _errorLiveData = MutableLiveData<Event<Int>>()
    val errorLiveData: LiveData<Event<Int>>
        get() = _errorLiveData

    fun getWeatherForCity(cityId: Int) {
        compositeDisposable.add(
            repository.getWeatherForCity(cityId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _currentWeatherUiState.value = CurrentWeatherUiState.Loading }
                .subscribe({
                    _currentWeatherUiState.value = CurrentWeatherUiState.DataLoaded(it)
                }, {
                    _errorLiveData.value = Event(R.string.error_city_weather)
                })
        )
    }

    fun getForecast(cityId: Int){
        compositeDisposable.add(
            repository.getForecast(cityId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _forecastUiState.value = ForecastUiState.Loading }
                .subscribe({
                    _forecastUiState.value = ForecastUiState.DataLoaded(it)
                }, {
                    _errorLiveData.value = Event(R.string.error_forecast)
                })
        )
    }
}