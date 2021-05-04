package com.aavashsthapit.myapplication

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application required for Dagger Hilt
 * To know the scope of the dependencies
 * Required to put it in manifest
 */
@HiltAndroidApp
class MetaApplication : Application()