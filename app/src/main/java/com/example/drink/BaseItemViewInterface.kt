package com.example.drink

interface BaseItemViewInterface {
    fun onSuccess(data: Any)
    fun onShowFullProgressView()
    fun onHideFullProgressView()
    fun onDialogNoInternet()
    fun onDialogError(message: String)
}