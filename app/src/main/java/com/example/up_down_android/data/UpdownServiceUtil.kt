package com.example.up_down_android.data

import com.google.gson.JsonObject
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface UpdownServiceUtil {
    @POST("media-project/up-down/accounts/sign-in")
    fun login(@Body body: JsonObject): Single<JsonObject>

    @POST("media-project/up-down/accounts/sign-up")
    fun signup(@Body body: JsonObject): Single<JsonObject>

    @POST("media-project/up-down/accounts/check-duplicate-phone-number")
    fun checkPhoneNumber(@Query("phoneNumber") phoneNumber : String): Completable

    @POST("media-project/up-down/accounts/check-duplicate-email")
    fun checkEmail(@Query("email") phoneNumber : String): Completable

    @POST("media-project/up-down/accounts/check-duplicate-name")
    fun checkName(@Query("username") phoneNumber : String): Completable

    @POST("media-project/up-down/bookmark-companies")
    fun bookmarkCompany(@Query("companyCode") companyCode : String): Single<JsonObject>

    @DELETE("media-project/up-down/bookmark-companies/{bookMarkedId}")
    fun deleteBookmarkCompany(@Path("bookMarkedId") bookMarkId : Int): Completable

    @GET("media-project/up-down/stocks/list")
    fun getCompany(@Query("q") query : String?) : Single<List<CompanyModel>>

    @GET("media-project/up-down/boards/list")
    fun getCommunityList(@Query("sortBy") sortBy : String) : Single<List<CommunityModel>>

    @POST("media-project/up-down/boards")
    fun postCommunity(@Body body : JsonObject) : Completable

    @PATCH("media-project/up-down/boards/{boardId}")
    fun patchCommunity(@Path("boardId") boardId : Int, @Body body : JsonObject) : Completable

    @DELETE("media-project/up-down/boards/{boardId}")
    fun deleteCommunity(@Path("boardId") boardId : Int) : Completable

    @GET("media-project/up-down/users/me")
    fun getMe() : Single<UserModel>

    @GET("media-project/up-down/users/my-boards")
    fun getMyCommunityList() : Single<List<CommunityModel>>

    @GET("media-project/up-down/boards/{boardId}/detail")
    fun getCommunityDetail(@Path("boardId") id : Int) : Single<CommunityModel>

    @POST("media-project/up-down/likes")
    fun postLike(@Query("boardId") boardId : Int, @Query("userId") userId : Int) : Completable

    @DELETE("media-project/up-down/likes/{boardLikeId}")
    fun deleteLike(@Path("boardLikeId") boardId : Int) : Completable

    @POST("media-project/up-down/comments")
    fun addComment(@Query("boardId") boardId : Int, @Body body: JsonObject) : Completable

    @DELETE("media-project/up-down/comments/{commentId}")
    fun deleteComment(@Path("commentId") id : Int) : Completable

    @GET("media-project/up-down/users/my-comments")
    fun getMyComment() : Single<List<CommentModel>>
}