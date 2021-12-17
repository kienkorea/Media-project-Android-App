package com.example.up_down_android.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.up_down_android.R
import com.example.up_down_android.databinding.DialogCommentBinding
import com.example.up_down_android.databinding.DialogCommunityBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CommunityDialog (val deleteAction : () -> Unit, val fixAction: () -> Unit) : BottomSheetDialogFragment() {

    private lateinit var dialogCommentBinding : DialogCommunityBinding

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
        dialogCommentBinding = DialogCommunityBinding.inflate(inflater, container, false)
        return dialogCommentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialogCommentBinding.textDelete.setOnClickListener {
            dialogCommentBinding.viewCommunity.visibility = View.GONE
            dialogCommentBinding.viewDelete.visibility = View.VISIBLE
            dialogCommentBinding.textTitle.text = "게시글 삭제"
        }
        dialogCommentBinding.textFix.setOnClickListener {
            dismiss()
            fixAction()
        }

        dialogCommentBinding.buttonDelete.setOnClickListener {
            dismiss()
            deleteAction()
        }
        dialogCommentBinding.buttonCancle.setOnClickListener {
            dismiss()
        }
    }
}
