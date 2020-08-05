package com.mpaja.weatherapplication.ui

import android.os.Bundle
import com.mpaja.weatherapplication.R
import com.mpaja.weatherapplication.ui.base.BaseActivity
import dagger.android.support.HasSupportFragmentInjector

class MainActivity : BaseActivity(), HasSupportFragmentInjector {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}