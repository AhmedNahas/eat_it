package com.example.myapplication

import android.app.Application
import android.content.Context
import com.example.myapplication.dataSource.AppPreferencesHelper

class MyApplication : Application() {
    val prefrences: AppPreferencesHelper
        get() = AppPreferencesHelper(context)

    override fun onCreate() {
        super.onCreate()
        context = this
    }

    companion object {
        lateinit var context: Context

        @JvmStatic
        fun getInstance(): MyApplication? {
            return MyApplication()
        }
    }

}