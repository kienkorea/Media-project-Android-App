package com.example.up_down_android.ui.post

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.example.up_down_android.R
import com.example.up_down_android.UpdownApplication
import com.example.up_down_android.adapter.CommentAdapter
import com.example.up_down_android.base.BaseActivity
import com.example.up_down_android.databinding.ActivityPostBinding
import com.example.up_down_android.dialog.CommentDialog
import com.example.up_down_android.dialog.CommunityDialog
import com.example.up_down_android.ui.write.WriteActivity
import com.google.gson.JsonObject
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers

class PostActivity : BaseActivity() {

    private val postViewBinding: ActivityPostBinding by lazy {
        ActivityPostBinding.inflate(layoutInflater)
    }

    private val detailId: Int by lazy {
        intent.getIntExtra("id", 0)
    }

    private var likeId = 0

    private val commentAdapter = CommentAdapter(true) {
        CommentDialog {
            deleteComment(it.id)
        }.show(supportFragmentManager, null)
    }

    private var isLiked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(postViewBinding.root)

        postViewBinding.icBack.setOnClickListener { finish() }
        postViewBinding.listComment.adapter = commentAdapter
        postViewBinding.editCommunity.addTextChangedListener {
            postViewBinding.icSend.isEnabled = it?.isEmpty() != true
        }
        postViewBinding.icSend.isEnabled = false

        postViewBinding.icSend.setOnClickListener { addComment() }

        postViewBinding.viewLike.setOnClickListener {
            if (isLiked) deleteLike()
            else changeLike()
        }

        postViewBinding.icInfo.setOnClickListener {
            CommunityDialog({
                deleteCommunity()
            }, {
                startActivity(
                    Intent(this, WriteActivity::class.java).putExtra("id", detailId)
                        .putExtra("comment", postViewBinding.textContent.text.toString())
                )
            }).show(supportFragmentManager, null)
        }
    }

    override fun onResume() {
        super.onResume()
        getPostDetail()
    }

    private fun changeLike() {
        updownServiceUtil.postLike(detailId, UpdownApplication.userModel?.id ?: 0)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                isLiked = true
                postViewBinding.icHeart.setImageResource(R.drawable.ic_heart_big_enabled)
                postViewBinding.textLikeCount.setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.text_red
                    )
                )
                getPostDetail()
            }, {
            }).addTo(compositeDisposable)
    }

    private fun deleteLike() {
        updownServiceUtil.deleteLike(likeId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                isLiked = false
                postViewBinding.icHeart.setImageResource(R.drawable.ic_heart_big_disabled)
                postViewBinding.textLikeCount.setTextColor(
                    ContextCompat.getColor(
                        this,
                        R.color.text_gray
                    )
                )
                getPostDetail()
            }, {
            }).addTo(compositeDisposable)
    }

    private fun getPostDetail() {
        updownServiceUtil.getCommunityDetail(detailId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                isLiked = it.isLiked
                likeId = it.likeId
                postViewBinding.textTitle.text = it.authorName
                postViewBinding.textDate.text = it.getDateInfo()
                postViewBinding.textContent.text = it.content
                postViewBinding.textLikeCount.text = "좋아요 ${it.totalLike}"
                postViewBinding.textComment.text = "댓글 ${it.totalComment}"

                if (it.isMyBoard) {
                    postViewBinding.icInfo.visibility = View.VISIBLE
                } else{
                    postViewBinding.icInfo.visibility = View.GONE
                }
                postViewBinding.icHeart.setImageResource(if (it.isLiked) R.drawable.ic_heart_big_enabled else R.drawable.ic_heart_big_disabled)
                postViewBinding.textLikeCount.setTextColor(
                    ContextCompat.getColor(
                        this,
                        if (it.isLiked) R.color.text_red else R.color.text_gray
                    )
                )
                commentAdapter.commentModel = it.commentListResponse
            }, {
            }).addTo(compositeDisposable)
    }

    private fun addComment() {
        val body = JsonObject().apply {
            addProperty("content", postViewBinding.editCommunity.text?.toString())
        }
        updownServiceUtil.addComment(detailId, body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                postViewBinding.editCommunity.setText("")
                getPostDetail()
            }, {
            }).addTo(compositeDisposable)
    }

    private fun deleteComment(id : Int) {
        updownServiceUtil.deleteComment(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                getPostDetail()
            }, {
            }).addTo(compositeDisposable)
    }

    private fun deleteCommunity() {
        updownServiceUtil.deleteCommunity(detailId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                finish()
            }, {
            }).addTo(compositeDisposable)
    }
}