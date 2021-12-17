package com.example.up_down_android.ui.history

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.up_down_android.R
import com.example.up_down_android.adapter.CommentAdapter
import com.example.up_down_android.adapter.CommunityAdapter
import com.example.up_down_android.base.BaseActivity
import com.example.up_down_android.databinding.ActivityHistoryBinding
import com.example.up_down_android.ui.detail.DetailActivity
import com.example.up_down_android.ui.post.PostActivity
import com.google.android.material.tabs.TabLayout
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers

class HistoryActivity : BaseActivity() {

    private val historyViewBinding : ActivityHistoryBinding by lazy {
        ActivityHistoryBinding.inflate(layoutInflater)
    }

    private val communityAdapter = CommunityAdapter{
        startActivity(Intent(this,PostActivity::class.java).putExtra("id",it.id))
    }

    private val commentAdapter = CommentAdapter(false) {
        startActivity(Intent(this, PostActivity::class.java).putExtra("id", it.boardId))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(historyViewBinding.root)

        historyViewBinding.icBack.setOnClickListener { finish() }
        historyViewBinding.viewTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position){
                    0 -> getCommunity()
                    else -> getComment()
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    override fun onResume() {
        super.onResume()
        historyViewBinding.viewTab.getTabAt(0)?.select()
        getCommunity()
    }

    private fun getCommunity() {
        historyViewBinding.listHistory.adapter = communityAdapter
        communityAdapter.communityList = listOf()
        updownServiceUtil.getMyCommunityList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                communityAdapter.communityList = it
            }, {}).addTo(compositeDisposable)
    }

    private fun getComment(){
        historyViewBinding.listHistory.adapter = commentAdapter
        commentAdapter.commentModel = listOf()
        updownServiceUtil.getMyComment()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                commentAdapter.commentModel = it
            }, {}).addTo(compositeDisposable)
    }
}