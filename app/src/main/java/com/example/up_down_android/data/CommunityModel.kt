package com.example.up_down_android.data

import android.util.Log
import com.example.up_down_android.util.LocalDateTimeConverter
import com.google.gson.annotations.JsonAdapter
import org.joda.time.Days
import org.joda.time.LocalDateTime
import org.joda.time.Months
import org.joda.time.Period

data class CommunityModel(
    val authorName : String,
    val id : Int,
    val isLiked : Boolean,
    val isMyBoard : Boolean,
    val totalLike : Int,
    val totalComment : Int,
    val commentListResponse : List<CommentModel>,
    val content : String,
    val likeId : Int = 0,
    @JsonAdapter(LocalDateTimeConverter::class) val createAt : LocalDateTime
){
    fun getDateInfo() : String{
        val day = Days.daysBetween(createAt,LocalDateTime.now())
        val period = Period(createAt,LocalDateTime.now())
        val month = Months.monthsBetween(createAt,LocalDateTime.now())

        return when{
            month.months > 0 -> "${month.months}달 전"
            day.days > 0 -> "${day.days}일 전"
            period.hours > 0 -> "${period.hours}시간 전"
            period.minutes > 0 -> "${period.minutes}분 전"
            else -> "방금 전"
        }
    }
}


data class CommentModel(
    val boardId : Int,
    @JsonAdapter(LocalDateTimeConverter::class) val createdAt : LocalDateTime,
    val id : Int,
    val isMyComment : Boolean,
    val userId : Int,
    val userName : String,
    val content : String
){
    fun getDateInfo() : String{
        val day = Days.daysBetween(createdAt,LocalDateTime.now())
        val period = Period(createdAt,LocalDateTime.now())
        val month = Months.monthsBetween(createdAt,LocalDateTime.now())

        return when{
            month.months > 0 -> "${month.months}달 전"
            day.days > 0 -> "${day.days}일 전"
            period.hours > 0 -> "${period.hours}시간 전"
            period.minutes > 0 -> "${period.minutes}분 전"
            else -> "방금 전"
        }
    }
}
