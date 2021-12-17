package com.example.up_down_android.ui.signup.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.up_down_android.R
import com.example.up_down_android.data.UpdownServiceUtil
import com.example.up_down_android.databinding.FragmentPhoneBinding
import com.example.up_down_android.ui.signup.SignupActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.android.ext.android.inject

class PhoneFragment : Fragment() {
    private var _phoneFragment: FragmentPhoneBinding? = null
    val phoneFragment get() = _phoneFragment

    private val compositeDisposable = CompositeDisposable()
    private val updownServiceUtil: UpdownServiceUtil by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        if (_phoneFragment == null) {
            _phoneFragment =
                FragmentPhoneBinding.inflate(layoutInflater, container, false)

            phoneFragment?.icBack?.setOnClickListener { requireActivity().finish() }
            phoneFragment?.editInfo?.addTextChangedListener { checkEnabled() }
            phoneFragment?.buttonSignup?.setOnClickListener { checkPhoneNumber() }
        }
        return phoneFragment?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    private fun checkEnabled() {
        phoneFragment?.buttonSignup?.isEnabled =
            !(phoneFragment?.editInfo?.text?.isEmpty() ?: false)
    }

    private fun checkPhoneNumber() {
        updownServiceUtil.checkPhoneNumber(phoneFragment?.editInfo?.text?.toString() ?: "")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                (activity as SignupActivity).signupModel.phoneNumber = phoneFragment?.editInfo?.text?.toString() ?: ""
                findNavController().navigate(R.id.action_phoneFragment_to_emailFragment)
            }, {
                phoneFragment?.textError?.visibility = View.VISIBLE
            }).addTo(compositeDisposable)
    }
}