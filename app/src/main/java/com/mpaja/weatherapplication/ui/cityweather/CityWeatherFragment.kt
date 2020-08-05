package com.mpaja.weatherapplication.ui.cityweather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.mpaja.weatherapplication.R
import com.mpaja.weatherapplication.data.repository.model.ForecastModel
import com.mpaja.weatherapplication.data.repository.model.WeatherModel
import com.mpaja.weatherapplication.databinding.FragmentCityWeatherBinding
import com.mpaja.weatherapplication.di.Injectable
import com.mpaja.weatherapplication.di.ViewModelInjectionFactory
import com.mpaja.weatherapplication.ui.base.BaseFragment
import com.mpaja.weatherapplication.ui.cityweather.adapter.ForecastRvAdapter
import com.mpaja.weatherapplication.utils.EventObserver
import com.mpaja.weatherapplication.utils.viewModelProvider
import kotlinx.android.synthetic.main.fragment_city_weather.*
import javax.inject.Inject

class CityWeatherFragment : BaseFragment(), Injectable {

    @Inject
    lateinit var viewModelInjectionFactory: ViewModelInjectionFactory<CityWeatherViewModel>

    lateinit var viewModel: CityWeatherViewModel
    lateinit var binding: FragmentCityWeatherBinding

    lateinit var forecastAdapter: ForecastRvAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_city_weather, container, false)
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = viewModelProvider(viewModelInjectionFactory)

        arguments?.let {
            if(it.getInt("cityId") != 0){
                viewModel.getWeatherForCity(it.getInt("cityId"))
                viewModel.getForecast(it.getInt("cityId"))
            }
        }

        prepareRecyclerView()
        registerObservables()
    }

    private fun registerObservables() {
        viewModel.currentWeatherUiState.observe(viewLifecycleOwner, Observer {state ->
            when(state){
                CurrentWeatherUiState.Loading -> progress_header.visibility = View.VISIBLE
                is CurrentWeatherUiState.DataLoaded -> {
                    progress_header.visibility = View.GONE
                    binding.weatherModel = state.weatherModel
                    val icon = state.weatherModel.iconCode
                    Glide.with(this)
                        .load("https://openweathermap.org/img/wn/$icon@2x.png")
                        .into(image_weather)
                }
            }

        })
        viewModel.forecastUiState.observe(viewLifecycleOwner, Observer {state ->
            when(state){
                ForecastUiState.Loading -> {
                    progress_forecast.visibility = View.VISIBLE
                    recycler_forecast.visibility = View.GONE
                }
                is ForecastUiState.DataLoaded -> {
                    progress_forecast.visibility = View.GONE
                    recycler_forecast.visibility = View.VISIBLE
                    forecastAdapter.addForecast(state.forecastModel)
                }
            }
        })
        viewModel.errorLiveData.observe(viewLifecycleOwner, EventObserver{
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun prepareRecyclerView() {
        forecastAdapter = ForecastRvAdapter()
        recycler_forecast.adapter = forecastAdapter
        recycler_forecast.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

    }


}

sealed class CurrentWeatherUiState{
    object Loading: CurrentWeatherUiState()
    data class DataLoaded(val weatherModel: WeatherModel): CurrentWeatherUiState()
}

sealed class ForecastUiState{
    object Loading: ForecastUiState()
    data class DataLoaded(val forecastModel: List<ForecastModel>): ForecastUiState()
}

