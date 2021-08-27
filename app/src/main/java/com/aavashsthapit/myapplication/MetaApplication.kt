package com.aavashsthapit.myapplication

import android.app.Application
import androidx.databinding.DataBindingUtil
import com.aavashsthapit.myapplication.databinding.BindingComponentBuilder
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import javax.inject.Provider

/**
 * Application required for Dagger Hilt
 * To know the scope of the dependencies
 * Required to put it in manifest
 */
@HiltAndroidApp
class MetaApplication : Application() {

    @Inject
    lateinit var bindingComponentProvider: Provider<BindingComponentBuilder>

    override fun onCreate() {
        super.onCreate()
        DataBindingUtil.setDefaultComponent(bindingComponentProvider.get().build())
    }
}