package com.example.up_down_android.ui.profile

import android.os.Bundle
import com.example.up_down_android.base.BaseActivity
import com.example.up_down_android.databinding.ActivityProfileBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers

class ProfileActivity : BaseActivity() {

    private val profileViewBinding: ActivityProfileBinding by lazy {
        ActivityProfileBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(profileViewBinding.root)
        profileViewBinding.icBack.setOnClickListener { finish() }

        getUserInfo()
    }

    private fun getUserInfo() {
        updownServiceUtil.getMe()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                profileViewBinding.textName.text = it.name
                profileViewBinding.textPhone.text = it.phoneNumber
                profileViewBinding.textEmail.text = it.email
            }, {}).addTo(compositeDisposable)
    }
}