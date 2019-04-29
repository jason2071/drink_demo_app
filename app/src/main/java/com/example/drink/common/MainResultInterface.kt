package com.example.drink.common

interface MainResultInterface {
    fun success(data: Any?)
    fun error(message: String)
}