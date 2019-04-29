package com.example.drink.manager.Http

import com.example.drink.banner.BannerResponse
import com.example.drink.common.MainResultInterface
import com.example.drink.drink.DrinkResponse
import com.example.drink.menu.MenuResponse
import com.example.drink.register.PassLoginModel
import com.example.drink.register.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitManager(var mainResultInterface: MainResultInterface) {

    private var mService: ApiService = RetrofitClient.apiService

    fun checkUser(passParameterModel: PassLoginModel) {
        val callUserLogin = mService.requestCheckUser(passParameterModel.phone)
        callUserLogin.enqueue(object : Callback<UserResponse> {
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                mainResultInterface.error("checkUser: Failure")
            }

            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    mainResultInterface.success(response.body())
                } else {
                    mainResultInterface.error("checkUser: Response error.")
                }
            }
        })
    }

    fun userRegister(passParameterModel: PassLoginModel) {
        val callUserRegister = mService.userRegister(
            passParameterModel.phone
            , passParameterModel.name
            , passParameterModel.address
            , passParameterModel.birthday
        )
        callUserRegister.enqueue(object : Callback<UserResponse> {
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                mainResultInterface.error("callUserRegister: Failure")
            }

            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    mainResultInterface.success(response.body())
                } else {
                    mainResultInterface.error("callUserRegister: Response error.")
                }
            }
        })
    }

    fun getBanner() {
        val callGetBanner = mService.getBanner()
        callGetBanner.enqueue(object : Callback<BannerResponse>{
            override fun onFailure(call: Call<BannerResponse>, t: Throwable) {
                mainResultInterface.error("callGetBanner: Failure")
            }

            override fun onResponse(call: Call<BannerResponse>, response: Response<BannerResponse>) {
                if (response.isSuccessful) {
                    mainResultInterface.success(response.body())
                } else {
                    mainResultInterface.error("callGetBanner: Response error.")
                }
            }
        })
    }

    fun getMenu() {
        val callMenu = mService.getMenu()
        callMenu.enqueue(object : Callback<MenuResponse>{
            override fun onFailure(call: Call<MenuResponse>, t: Throwable) {
                mainResultInterface.error("callPopularMenu: Failure")
            }

            override fun onResponse(call: Call<MenuResponse>, response: Response<MenuResponse>) {
                if (response.isSuccessful) {
                    mainResultInterface.success(response.body())
                } else {
                    mainResultInterface.error("callPopularMenu: Response error.")
                }
            }
        })
    }

    fun getDrink(menu_id: Int) {
        val callDrink = mService.getDrink(menu_id)
        callDrink.enqueue(object : Callback<DrinkResponse>{
            override fun onFailure(call: Call<DrinkResponse>, t: Throwable) {
                mainResultInterface.error("callDrink: Failure")
            }

            override fun onResponse(call: Call<DrinkResponse>, response: Response<DrinkResponse>) {
                if (response.isSuccessful) {
                    mainResultInterface.success(response.body())
                } else {
                    mainResultInterface.error("callDrink: Response error.")
                }
            }
        })
    }
}