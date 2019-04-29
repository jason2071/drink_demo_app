package com.example.drink.manager.Http

import com.example.drink.banner.BannerResponse
import com.example.drink.drink.DrinkResponse
import com.example.drink.menu.MenuResponse
import com.example.drink.register.UserResponse
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.POST
import retrofit2.http.FormUrlEncoded


interface ApiService {

    @FormUrlEncoded
    @POST("user/checkuser")
    fun requestCheckUser(@Field("phone") phone: String): Call<UserResponse>

    @FormUrlEncoded
    @POST("user/register")
    fun userRegister(
        @Field("phone") phone: String
        , @Field("name") name: String
        , @Field("address") address: String
        , @Field("birthday") birthday: String
    ): Call<UserResponse>

    @GET("banner")
    fun getBanner(): Call<BannerResponse>

    @GET("menu/all")
    fun getMenu(): Call<MenuResponse>

    @GET("menu/by")
    fun getDrink(@Query("menu_id") menu_id: Int): Call<DrinkResponse>
}