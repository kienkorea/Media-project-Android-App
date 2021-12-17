package com.example.up_down_android.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.up_down_android.R
import com.example.up_down_android.databinding.DialogCommentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CommentDialog (val successAction : () -> Unit) : BottomSheetDialogFragment() {

    private lateinit var dialogCommentBinding : DialogCommentBinding

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
        dialogCommentBinding = DialogCommentBinding.inflate(inflater, container, false)
        return dialogCommentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialogCommentBinding.viewChat.setOnClickListener {
            dialogCommentBinding.viewChat.visibility = View.GONE
            dialogCommentBinding.viewDelete.visibility = View.VISIBLE
            dialogCommentBinding.textTitle.text = "댓글 삭제"
        }

        dialogCommentBinding.buttonDelete.setOnClickListener {
            successAction()
            dismiss()
        }

        dialogCommentBinding.buttonCancle.setOnClickListener {
            dismiss()
        }
    }
}
