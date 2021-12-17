package com.example.up_down_android.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.up_down_android.R
import com.example.up_down_android.data.CompanyModel
import com.example.up_down_android.databinding.ItemStockBinding

class CompanyAdapter(private val clickEvent: (CompanyModel) -> Unit) :
    RecyclerView.Adapter<CompanyAdapter.CompanyViewHolder>() {

    var companyModel = listOf<CompanyModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyViewHolder {
        val view = ItemStockBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CompanyViewHolder(view)
    }

    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        holder.bind(companyModel[position])
    }

    override fun getItemCount(): Int = companyModel.size

    inner class CompanyViewHolder(private val viewBinding: ItemStockBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(item: CompanyModel) {

            if (item.isBookmarked) viewBinding.icHeart.visibility = View.VISIBLE
            viewBinding.textStockTitle.text = item.name
            viewBinding.textStockGrowth.text = item.changeRate + "%"
            viewBinding.textStockPrice.text = item.stockPrice + "원"

            Glide.with(viewBinding.root.context)
                .load(item.companyImageUrl)
                .fitCenter()
                .into(viewBinding.icCompany)

            if(item.changeRate.contains("-")){
                viewBinding.textStockGrowth.setTextColor(ContextCompat.getColor(viewBinding.root.context,
                    R.color.color_down))
            }else{
                viewBinding.textStockGrowth.setTextColor(ContextCompat.getColor(viewBinding.root.context,
                    R.color.text_red))
            }

            viewBinding.root.setOnClickListener { clickEvent(item) }
        }
    }
}