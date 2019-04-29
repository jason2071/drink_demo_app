package com.example.drink

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.facebook.accountkit.Account
import com.facebook.accountkit.AccountKit
import com.facebook.accountkit.AccountKitCallback
import com.facebook.accountkit.AccountKitError
import kotlinx.android.synthetic.main.activity_success.*

class Success : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)

        btnLogOut.setOnClickListener {
            AccountKit.logOut()
            finish()
        }
    }

    override fun onResume() {
        super.onResume()

        AccountKit.getCurrentAccount(object : AccountKitCallback<Account> {
            @SuppressLint("SetTextI18n")
            override fun onSuccess(account: Account?) {

                editUserId.setText("User Id ${account!!.id}")

                var email = ""
                var phone = ""

                if (account.email != null) {
                    email = account.email.toString()
                }

                if (account.phoneNumber != null) {
                    phone = account.phoneNumber.toString()
                }


                editEmail.setText("Email $email")
                editPhone.setText("Phone $phone")

            }

            override fun onError(accountKitError: AccountKitError?) {

            }
        })
    }
}
