package com.example.up_down_android.ui.signup.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.up_down_android.data.SignupModel
import com.example.up_down_android.databinding.FragmentCompleteBinding
import com.example.up_down_android.ui.main.MainActivity
import com.example.up_down_android.ui.signup.SignupActivity

class CompleteFragment : Fragment() {
    private var _completeFragment: FragmentCompleteBinding? = null
    val completeFragment get() = _completeFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        if (_completeFragment == null) {
            _completeFragment =
                FragmentCompleteBinding.inflate(layoutInflater, container, false)
            completeFragment?.textName?.text = (activity as SignupActivity).signupModel.name

            Handler(Looper.getMainLooper()).postDelayed({
                requireActivity().startActivity(Intent(requireActivity(), MainActivity::class.java))
                requireActivity().finishAffinity()
            }, 2000)
        }
        return completeFragment?.root
    }
}