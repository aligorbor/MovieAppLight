package ru.geekbrains.android2.movieapplight

import android.app.Application

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {
        lateinit var appInstance: App
    }
}