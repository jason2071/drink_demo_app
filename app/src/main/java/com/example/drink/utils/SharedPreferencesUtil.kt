package com.example.drink.utils

import android.content.Context
import android.preference.PreferenceManager


class SharedPreferencesUtil(private val mContext: Context) {

    companion object {
        const val SHARE_PRE_USER_PHONE = "userPhone"
    }

    fun saveUserPhone(phone: String) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext)
        val editor = sharedPreferences.edit()
        editor.putString(SHARE_PRE_USER_PHONE, phone)
        editor.apply()
    }

    val userPhone: String?
        get() {
            val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext)
            return sharedPreferences.getString(SHARE_PRE_USER_PHONE, "")
        }
}