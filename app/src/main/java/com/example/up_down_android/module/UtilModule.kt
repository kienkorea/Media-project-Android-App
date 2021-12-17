package com.example.up_down_android.module

import com.example.up_down_android.util.PrefUtil
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val utilModule = module {
    single { PrefUtil(androidContext()) }
}