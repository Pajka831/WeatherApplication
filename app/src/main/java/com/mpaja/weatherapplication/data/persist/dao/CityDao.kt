package com.mpaja.weatherapplication.data.persist.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mpaja.weatherapplication.data.persist.entity.CityEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface CityDao {

    @Query("SELECT * FROM city ORDER BY id DESC")
    fun getAllCities(): Single<List<CityEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(citiesList: List<CityEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCity(city: CityEntity): Completable
}