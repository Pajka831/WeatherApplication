package com.mpaja.weatherapplication.di.modules

import com.mpaja.weatherapplication.di.ActivityScope
import com.mpaja.weatherapplication.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBuildersModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeMainActivity(): MainActivity
}