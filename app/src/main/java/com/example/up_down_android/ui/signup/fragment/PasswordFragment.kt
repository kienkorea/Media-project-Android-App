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
import com.example.up_down_android.databinding.FragmentPasswordBinding
import com.example.up_down_android.ui.signup.SignupActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.android.ext.android.inject

class PasswordFragment : Fragment() {
    private var _passwordFragment: FragmentPasswordBinding? = null
    val passwordFragment get() = _passwordFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        if (_passwordFragment == null) {
            _passwordFragment =
                FragmentPasswordBinding.inflate(layoutInflater, container, false)

            passwordFragment?.icBack?.setOnClickListener { findNavController().popBackStack() }
            passwordFragment?.editInfo?.addTextChangedListener { checkEnabled() }
            passwordFragment?.buttonSignup?.setOnClickListener { checkPassword() }
        }
        return passwordFragment?.root
    }

    private fun checkEnabled() {
        passwordFragment?.buttonSignup?.isEnabled =
            !(passwordFragment?.editInfo?.text?.isEmpty() ?: false)
    }

    private fun checkPassword() {
        (activity as SignupActivity).signupModel.password = passwordFragment?.editInfo?.text?.toString() ?: ""
        findNavController().navigate(R.id.action_passwordFragment_to_nameFragment)
    }
}