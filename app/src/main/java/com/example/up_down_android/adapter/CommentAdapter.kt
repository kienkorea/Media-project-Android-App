package com.example.up_down_android.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.up_down_android.data.CommentModel
import com.example.up_down_android.databinding.ItemChatBinding

class CommentAdapter(private val moreVisible : Boolean,private val clickAction: (CommentModel) -> Unit) :
    RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    var commentModel = listOf<CommentModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CommentAdapter.CommentViewHolder {
        val binding =
            ItemChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CommentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CommentAdapter.CommentViewHolder, position: Int) {
        holder.bind(commentModel[position])
    }

    override fun getItemCount(): Int = commentModel.size

    inner class CommentViewHolder(private val commentBinding: ItemChatBinding) :
        RecyclerView.ViewHolder(commentBinding.root) {
        fun bind(data: CommentModel) {
            commentBinding.textTitle.text = data.userName
            commentBinding.textContent.text = data.content
            commentBinding.textDate.text = data.getDateInfo()

            if (moreVisible && data.isMyComment){
                commentBinding.icMore.visibility = View.VISIBLE
            }else{
                commentBinding.icMore.visibility = View.GONE
            }

            if(!moreVisible) commentBinding.root.setOnClickListener { clickAction(data) }
            else commentBinding.icMore.setOnClickListener { clickAction(data) }
        }
    }
}