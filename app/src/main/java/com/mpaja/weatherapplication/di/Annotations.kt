@file:JvmName("Annotations")

package com.mpaja.weatherapplication.di

import javax.inject.Scope

/**
 * Dagger scope for Activities
 */
@Scope
annotation class ActivityScope

/**
 * Dagger scope for Fragments
 */
@Scope
annotation class FragmentScope

/**
 * Dagger scope for Services
 */
@Scope
annotation class ServiceScope
