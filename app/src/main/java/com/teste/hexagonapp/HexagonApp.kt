package com.teste.hexagonapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HexagonApp : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}