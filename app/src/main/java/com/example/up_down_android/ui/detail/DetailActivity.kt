package com.example.up_down_android.ui.detail

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.up_down_android.R
import com.example.up_down_android.base.BaseActivity
import com.example.up_down_android.data.CompanyModel
import com.example.up_down_android.databinding.ActivityDetailBinding
import com.google.gson.Gson
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers

class DetailActivity : BaseActivity() {

    private val companyModel: CompanyModel by lazy {
        Gson().fromJson(intent.getStringExtra("value"), CompanyModel::class.java)
    }

    private val detailViewBinding: ActivityDetailBinding by lazy {
        ActivityDetailBinding.inflate(layoutInflater)
    }

    private var isBookmark = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(detailViewBinding.root)

        detailViewBinding.icBack.setOnClickListener { finish() }
        detailViewBinding.textTitle.text = companyModel.name
        detailViewBinding.textPrice.text = companyModel.stockPrice

        detailViewBinding.viewLike.setOnClickListener {
            if(isBookmark) deleteBookmark()
            else setBookmark()
        }

        isBookmark = companyModel.isBookmarked
        drawNews()
        drawBookmarked()
        setPrice()
    }

    private fun drawBookmarked(){
        if(isBookmark){
            detailViewBinding.viewLike.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.color_red_transparent))
            detailViewBinding.icHeart.setImageResource(R.drawable.ic_heart_full_abled)
            detailViewBinding.textLike.setTextColor(ContextCompat.getColor(this,R.color.text_red))
        }else{
            detailViewBinding.viewLike.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.color_gray_transparent))
            detailViewBinding.icHeart.setImageResource(R.drawable.ic_heart_full_enabled)
            detailViewBinding.textLike.setTextColor(ContextCompat.getColor(this,R.color.text_gray))
        }
    }

    private fun setPrice(){
        if(companyModel.changeRate.contains("-")){
            detailViewBinding.textInfo.setTextColor(ContextCompat.getColor(this,R.color.color_down))
            detailViewBinding.textInfo.text = "-${companyModel.changeVal}원 (${companyModel.changeRate.replace("-","")}%)"
        }else{
            detailViewBinding.textInfo.setTextColor(ContextCompat.getColor(this,R.color.text_red))
            detailViewBinding.textInfo.text = "+${companyModel.changeVal}원 (${companyModel.changeRate.replace("+","")}%)"
        }
    }

    private fun drawNews() {
        detailViewBinding.listNews.removeAllViews()

        companyModel.naverNewsResponseList.forEach { model ->
            val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val newsView = inflater.inflate(R.layout.item_news, null)

            newsView.findViewById<TextView>(R.id.text_title).text = model.title
            val image = newsView.findViewById<ImageView>(R.id.ic_news)

            Glide.with(this)
                .load(model.thumbnailImageUrl)
                .fitCenter()
                .into(image)

            newsView.setOnClickListener {
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(model.naverNewsUrl)
                    )
                )
            }

            detailViewBinding.listNews.addView(newsView)
        }
    }

    private fun setBookmark(){
        updownServiceUtil.bookmarkCompany(companyModel.companyCode)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                isBookmark = true
                drawBookmarked()
            }, {}).addTo(compositeDisposable)
    }

    private fun deleteBookmark(){

    }
}