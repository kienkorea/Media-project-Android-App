package com.example.up_down_android.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.up_down_android.R
import com.example.up_down_android.data.CommunityModel
import com.example.up_down_android.databinding.ItemCommunityBinding

class CommunityAdapter(private val clickAction: (CommunityModel) -> Unit) :
    RecyclerView.Adapter<CommunityAdapter.ReservationViewHolder>() {

    var communityList = listOf<CommunityModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CommunityAdapter.ReservationViewHolder {
        val binding =
            ItemCommunityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReservationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommunityAdapter.ReservationViewHolder, position: Int) {
        holder.bind(communityList[position])
    }

    override fun getItemCount(): Int = communityList.size

    inner class ReservationViewHolder(private val communityBinding: ItemCommunityBinding) :
        RecyclerView.ViewHolder(communityBinding.root) {
        fun bind(data: CommunityModel) {
            communityBinding.textTitle.text = data.authorName
            communityBinding.textContent.text = data.content
            communityBinding.textDate.text = data.getDateInfo()
            communityBinding.icHeart.setImageResource(if(data.isLiked) R.drawable.ic_heart_enabled else R.drawable.ic_heart_disabled)
            communityBinding.textLikeCount.text = "${data.totalLike}개"
            communityBinding.textLikeCount.setTextColor(
                ContextCompat.getColor(
                    communityBinding.root.context,
                    if (data.isLiked) R.color.text_red else R.color.text_gray
                )
            )

            communityBinding.textCommunityCount.text = "${data.totalComment}개"
            communityBinding.root.setOnClickListener { clickAction(data) }
        }
    }
}