package com.example.up_down_android.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import com.example.up_down_android.base.BaseActivity
import com.example.up_down_android.databinding.ActivityLoginBinding
import com.example.up_down_android.ui.main.MainActivity
import com.example.up_down_android.ui.signup.SignupActivity
import com.google.gson.JsonObject
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers

class LoginActivity : BaseActivity() {

    private val loginViewBinding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(loginViewBinding.root)

        loginViewBinding.textSignup.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        loginViewBinding.editId.addTextChangedListener { checkEnabled() }
        loginViewBinding.editPassword.addTextChangedListener { checkEnabled() }
        loginViewBinding.buttonSignup.setOnClickListener { login() }
    }

    private fun checkEnabled() {
        loginViewBinding.buttonSignup.isEnabled =
            !(loginViewBinding.editId.text.isNullOrEmpty() || loginViewBinding.editPassword.text.isNullOrEmpty())
    }

    private fun login() {
        val body = JsonObject().apply {
            addProperty("loginPassWord", loginViewBinding.editPassword.text.toString())
            addProperty("phoneNumber", loginViewBinding.editId.text.toString())
        }
        updownServiceUtil.login(body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                prefUtil.setUserAccessToken(it.get("authorization").asJsonObject.get("accessToken").asString)
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }, {
                loginViewBinding.textError.visibility = View.VISIBLE
            }).addTo(compositeDisposable)
    }
}