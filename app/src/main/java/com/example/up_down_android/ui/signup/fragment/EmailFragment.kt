package com.example.up_down_android.ui.signup.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.up_down_android.R
import com.example.up_down_android.data.UpdownServiceUtil
import com.example.up_down_android.databinding.FragmentEmailBinding
import com.example.up_down_android.ui.signup.SignupActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.android.ext.android.inject

class EmailFragment : Fragment() {
    private var _emailFragment : FragmentEmailBinding? = null
    val emailFragment get() = _emailFragment

    private val compositeDisposable = CompositeDisposable()
    private val updownServiceUtil: UpdownServiceUtil by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        if (_emailFragment == null) {
            _emailFragment =
                FragmentEmailBinding.inflate(layoutInflater, container, false)

            emailFragment?.icBack?.setOnClickListener { findNavController().popBackStack() }
            emailFragment?.editInfo?.addTextChangedListener { checkEnabled() }
            emailFragment?.buttonSignup?.setOnClickListener { checkEmail() }
        }
        return emailFragment?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    private fun checkEnabled() {
        emailFragment?.buttonSignup?.isEnabled =
            !(emailFragment?.editInfo?.text?.isEmpty() ?: false)
    }

    private fun checkEmail() {
        updownServiceUtil.checkEmail(emailFragment?.editInfo?.text?.toString() ?: "")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                (activity as SignupActivity).signupModel.email = emailFragment?.editInfo?.text?.toString() ?: ""
                findNavController().navigate(R.id.action_emailFragment_to_passwordFragment)
            }, {
                emailFragment?.textError?.visibility = View.VISIBLE
            }).addTo(compositeDisposable)
    }
}