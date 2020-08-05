package com.mpaja.weatherapplication.ui.base

import androidx.fragment.app.Fragment
import com.mpaja.weatherapplication.di.Injectable
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

abstract class BaseFragment : Fragment(), HasSupportFragmentInjector, Injectable {
    @Inject
    lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector() = childFragmentInjector
}