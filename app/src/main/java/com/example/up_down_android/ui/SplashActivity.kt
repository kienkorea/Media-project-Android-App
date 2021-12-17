package com.example.up_down_android.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.up_down_android.R
import com.example.up_down_android.UpdownApplication
import com.example.up_down_android.base.BaseActivity
import com.example.up_down_android.ui.login.LoginActivity
import com.example.up_down_android.ui.main.MainActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            if (prefUtil.getUserAccessToken() == null){
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
            else{
                getMe()
            }
        }, 500)
    }

    private fun getMe(){
        updownServiceUtil.getMe()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                UpdownApplication.userModel = it
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }, {
            }).addTo(compositeDisposable)
    }
}