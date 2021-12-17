package com.example.up_down_android.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.up_down_android.R
import com.example.up_down_android.databinding.DialogCommentBinding
import com.example.up_down_android.databinding.DialogLogoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class LogoutDialog(val successAction : () -> Unit) : BottomSheetDialogFragment() {

    private lateinit var dialogLogoutBinding : DialogLogoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(
            DialogFragment.STYLE_NORMAL,
            R.style.Theme_Up_down_android_BottomSheetDialogStyle
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialogLogoutBinding = DialogLogoutBinding.inflate(inflater, container, false)
        return dialogLogoutBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        dialogLogoutBinding.buttonDelete.setOnClickListener {
            successAction()
            dismiss()
        }

        dialogLogoutBinding.buttonCancle.setOnClickListener { dismiss() }
    }
}
