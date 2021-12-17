package com.example.up_down_android.data

import com.google.gson.annotations.SerializedName

data class CompanyModel(
    @SerializedName("change_rate")
    val changeRate : String,
    @SerializedName("change_val")
    val changeVal : String,
    val companyCode : String,
    val isBookmarked : Boolean,
    val name : String,
    val risefall : String,
    val stockPrice : String,
    val companyImageUrl : String,
    val naverNewsResponseList : List<NewsModel>
)

data class NewsModel(
    val naverNewsUrl : String,
    val thumbnailImageUrl : String,
    val title : String
)