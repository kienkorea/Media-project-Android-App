package com.example.up_down_android.base

import androidx.appcompat.app.AppCompatActivity
import com.example.up_down_android.data.UpdownServiceUtil
import com.example.up_down_android.util.PrefUtil
import io.reactivex.rxjava3.disposables.CompositeDisposable
import org.koin.android.ext.android.inject

open class BaseActivity : AppCompatActivity(){

    val compositeDisposable = CompositeDisposable()
    val updownServiceUtil : UpdownServiceUtil by inject()
    val prefUtil : PrefUtil by inject()

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

}