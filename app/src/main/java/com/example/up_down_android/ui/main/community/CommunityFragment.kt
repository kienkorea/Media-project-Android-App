package com.example.up_down_android.ui.main.community

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.up_down_android.adapter.CommunityAdapter
import com.example.up_down_android.data.CommunityModel
import com.example.up_down_android.data.UpdownServiceUtil
import com.example.up_down_android.databinding.FragmentCommunityBinding
import com.example.up_down_android.databinding.FragmentInfoBinding
import com.example.up_down_android.ui.post.PostActivity
import com.example.up_down_android.ui.search.SearchActivity
import com.example.up_down_android.ui.write.WriteActivity
import com.google.android.material.tabs.TabLayout
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.android.ext.android.inject

class CommunityFragment : Fragment() {
    private var _communityBinding : FragmentCommunityBinding? = null
    private val communityBinding get() = _communityBinding

    val compositeDisposable = CompositeDisposable()
    val updownServiceUtil: UpdownServiceUtil by inject()

    private val communityAdapter = CommunityAdapter{
        requireActivity().startActivity(Intent(requireActivity(), PostActivity::class.java).putExtra("id",it.id))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _communityBinding = FragmentCommunityBinding.inflate(layoutInflater, container, false)
        return communityBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        communityBinding?.viewWrite?.setOnClickListener {
            requireActivity().startActivity(Intent(requireActivity(),WriteActivity::class.java))
        }

        communityBinding?.viewTab?.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position){
                    0 -> getCommunityList("likeCount")
                    else -> getCommunityList("createdAt")
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        communityBinding?.listCommunity?.adapter = communityAdapter
    }

    override fun onResume() {
        super.onResume()
        communityBinding?.viewTab?.getTabAt(0)?.select()
        getCommunityList("likeCount")
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    private fun getCommunityList(query : String){
        updownServiceUtil.getCommunityList(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                communityAdapter.communityList = it
            }, {
            }).addTo(compositeDisposable)
    }
}