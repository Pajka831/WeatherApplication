package com.mpaja.weatherapplication.di.component

import android.app.Application
import com.mpaja.weatherapplication.WeatherApp
import com.mpaja.weatherapplication.di.modules.ActivityBuildersModule
import com.mpaja.weatherapplication.di.modules.ApiModule
import com.mpaja.weatherapplication.di.modules.AppModule
import com.mpaja.weatherapplication.di.modules.DataModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityBuildersModule::class,
        ApiModule::class,
        DataModule::class]
)
interface AppComponent {
    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: WeatherApp)
}