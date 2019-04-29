package com.example.drink.banner

import com.example.drink.BaseItemViewInterface
import com.example.drink.common.MainResultInterface
import com.example.drink.manager.Http.RetrofitManager
import com.example.drink.utils.NetworkUtil

class BannerPresenter(private val baseItemViewInterface: BaseItemViewInterface) : MainResultInterface {

    private var mainResultInterface: MainResultInterface = this
    private val retrofitManager = RetrofitManager(mainResultInterface)

    fun getBanner() {
        if (NetworkUtil().isNetworkAvailable) {
            baseItemViewInterface.onShowFullProgressView()
            retrofitManager.getBanner()
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