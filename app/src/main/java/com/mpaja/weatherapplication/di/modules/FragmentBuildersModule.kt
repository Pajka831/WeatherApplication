package com.mpaja.weatherapplication.di.modules

import com.mpaja.weatherapplication.di.FragmentScope
import com.mpaja.weatherapplication.ui.cityweather.CityWeatherFragment
import com.mpaja.weatherapplication.ui.searchcity.SearchCityFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeSearchCityFragment(): SearchCityFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeCityWeatherFragment(): CityWeatherFragment
}