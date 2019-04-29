package com.example.drink.register

import com.example.drink.BaseItemViewInterface
import com.example.drink.common.MainResultInterface
import com.example.drink.manager.Http.RetrofitManager
import com.example.drink.utils.NetworkUtil
import retrofit2.Call

class RegisterPresenter(
    private val baseItemViewInterface: BaseItemViewInterface
) : MainResultInterface {

    private var mainResultInterface: MainResultInterface = this
    private val retrofitManager = RetrofitManager(mainResultInterface)

    fun registerValidate(passLoginModel: PassLoginModel) {
        if (NetworkUtil().isNetworkAvailable) {
            baseItemViewInterface.onShowFullProgressView()
            retrofitManager.checkUser(passLoginModel)
        } else {
            baseItemViewInterface.onDialogNoInternet()
        }
    }

    fun userRegister(passLoginModel: PassLoginModel) {
        if (NetworkUtil().isNetworkAvailable) {
            baseItemViewInterface.onShowFullProgressView()
            retrofitManager.userRegister(passLoginModel)
        } else {
            baseItemViewInterface.onDialogNoInternet()
        }
    }

    override fun success(data: Any?) {
        baseItemViewInterface.onHideFullProgressView()
        baseItemViewInterface.onSuccess(data!!)
    }

    override fun error(message: String) {
        baseItemViewInterface.onHideFullProgressView()
        baseItemViewInterface.onDialogError(message)
    }

}