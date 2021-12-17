package com.example.up_down_android

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.up_down_android.data.UserModel
import com.example.up_down_android.module.networkMoudle
import com.example.up_down_android.module.utilModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class UpdownApplication : Application() {

    companion object{
        var userModel : UserModel? = null
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(listOf(networkMoudle,utilModule))
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            androidContext(this@UpdownApplication)
        }
    }
}