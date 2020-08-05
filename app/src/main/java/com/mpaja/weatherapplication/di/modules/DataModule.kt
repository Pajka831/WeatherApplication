package com.mpaja.weatherapplication.di.modules

import android.app.Application
import androidx.room.Room
import com.mpaja.weatherapplication.data.persist.WeatherDatabase
import com.mpaja.weatherapplication.data.persist.dao.CityDao
import com.mpaja.weatherapplication.data.repository.Repository
import com.mpaja.weatherapplication.data.repository.RepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class DataModule {

    @Binds
    abstract fun bindRepository(repository: RepositoryImpl): Repository

    @Module
    companion object {
        @Provides
        @Singleton
        @JvmStatic
        fun provideCityDao(weatherDatabase: WeatherDatabase): CityDao = weatherDatabase.cityDao()

        @Provides
        @Singleton
        @JvmStatic
        fun provideDatabase(app: Application): WeatherDatabase =
            Room.databaseBuilder(app, WeatherDatabase::class.java, WeatherDatabase.DB_NAME).build()
    }
}