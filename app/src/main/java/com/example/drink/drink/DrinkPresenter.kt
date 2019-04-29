package com.example.drink.drink

import com.example.drink.BaseItemViewInterface
import com.example.drink.common.MainResultInterface
import com.example.drink.manager.Http.RetrofitManager
import com.example.drink.utils.NetworkUtil

class DrinkPresenter(private val baseItemViewInterface: BaseItemViewInterface) : MainResultInterface {

    private var mainResultInterface: MainResultInterface = this
    private val retrofitManager = RetrofitManager(mainResultInterface)

    fun getDrink(menuId: Int) {
        if (NetworkUtil().isNetworkAvailable) {
            baseItemViewInterface.onShowFullProgressView()
            retrofitManager.getDrink(menuId)
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