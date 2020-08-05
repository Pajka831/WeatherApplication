package com.mpaja.weatherapplication.ui.searchcity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mpaja.weatherapplication.R
import com.mpaja.weatherapplication.data.repository.Repository
import com.mpaja.weatherapplication.data.repository.model.CityModel
import com.mpaja.weatherapplication.utils.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_city_weather.view.*
import javax.inject.Inject

class SearchCityViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _uiState = MutableLiveData<CityFragmentUiState>()
    val uiState: LiveData<CityFragmentUiState>
        get() = _uiState

    private val _errorLiveData = MutableLiveData<Event<Int>>()
    val errorLiveData: LiveData<Event<Int>>
        get() = _errorLiveData

    private var compositeDisposable = CompositeDisposable()

    fun getCitiesList(cityName: String) {
        compositeDisposable.add(
            repository.getCities(cityName).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _uiState.value = CityFragmentUiState.Loading }
                .subscribe({
                    _uiState.value = CityFragmentUiState.DataSearched(it)
                }, {
                    _errorLiveData.value = Event(R.string.error_search)
                })
        )
    }

    fun getHistory() {
        compositeDisposable.add(
            repository.getCitiesHistory().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { _uiState.value = CityFragmentUiState.Loading }
                .subscribe({
                    _uiState.value = CityFragmentUiState.DataLoaded(it)
                }, {
                    _errorLiveData.value = Event(R.string.error_history)
                })
        )
    }

    fun saveSearchHistory(cityName: String) {
        compositeDisposable.add(
            repository.saveCityToDatabase(CityModel(null, cityName, null))
                .subscribeOn(Schedulers.io())
                .subscribe()
        )
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}
