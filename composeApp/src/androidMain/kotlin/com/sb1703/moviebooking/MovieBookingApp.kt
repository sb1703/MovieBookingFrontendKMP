package com.sb1703.moviebooking

import android.app.Application
import di.initKoinAndroid
import org.koin.android.ext.koin.androidContext
import platform.AndroidApplicationComponent
import platform.application

class MovieBookingApp: Application() {
    override fun onCreate() {
        super.onCreate()
        initKoinAndroid(AndroidApplicationComponent()) {
            androidContext(this@MovieBookingApp)
        }
    }
}