package com.example.up_down_android.ui.signup.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.up_down_android.R
import com.example.up_down_android.data.UpdownServiceUtil
import com.example.up_down_android.databinding.FragmentNameBinding
import com.example.up_down_android.ui.main.MainActivity
import com.example.up_down_android.ui.signup.SignupActivity
import com.example.up_down_android.util.PrefUtil
import com.google.gson.JsonObject
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.android.ext.android.inject

class NameFragment : Fragment() {
    private var _nameFragment: FragmentNameBinding? = null
    val nameFragment get() = _nameFragment

    private val compositeDisposable = CompositeDisposable()
    private val updownServiceUtil: UpdownServiceUtil by inject()
    private val prefUtil : PrefUtil by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        if (_nameFragment == null) {
            _nameFragment =
                FragmentNameBinding.inflate(layoutInflater, container, false)

            nameFragment?.icBack?.setOnClickListener { findNavController().popBackStack() }
            nameFragment?.editInfo?.addTextChangedListener { checkEnabled() }
            nameFragment?.buttonSignup?.setOnClickListener { checkName() }
        }
        return nameFragment?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    private fun checkEnabled() {
        nameFragment?.buttonSignup?.isEnabled =
            !(nameFragment?.editInfo?.text?.isEmpty() ?: false)
    }

    private fun checkName() {
        (activity as SignupActivity).signupModel.name =
            nameFragment?.editInfo?.text?.toString() ?: ""
        updownServiceUtil.checkName(nameFragment?.editInfo?.text?.toString() ?: "")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                signup()
            }, {
                Log.e("asdfTest",it.toString())
                nameFragment?.textError?.visibility = View.VISIBLE
            }).addTo(compositeDisposable)
    }

    private fun signup() {
        val model = (activity as SignupActivity).signupModel
        val body = JsonObject().apply {
            addProperty("email", model.email)
            addProperty("loginPassword", model.password)
            addProperty("name", model.name)
            addProperty("phoneNumber", model.phoneNumber)
        }
        updownServiceUtil.signup(body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                prefUtil.setUserAccessToken(it.get("authorization").asJsonObject.get("accessToken").asString)
                findNavController().navigate(R.id.action_nameFragment_to_completeFragment)
            }, {
                nameFragment?.textError?.visibility = View.VISIBLE
            }).addTo(compositeDisposable)
    }
}