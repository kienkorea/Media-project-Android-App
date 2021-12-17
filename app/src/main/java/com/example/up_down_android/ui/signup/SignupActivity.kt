package com.example.up_down_android.ui.signup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.example.up_down_android.R
import com.example.up_down_android.data.SignupModel
import com.example.up_down_android.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {
    private val signupViewBinding : ActivitySignupBinding by lazy {
        ActivitySignupBinding.inflate(layoutInflater)
    }

    val signupModel = SignupModel("","","","")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(signupViewBinding.root)
    }

    override fun onSupportNavigateUp() = findNavController(R.id.fragment_signup).navigateUp()
}