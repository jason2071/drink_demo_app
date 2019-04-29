package com.example.drink

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import com.example.drink.common.Common
import com.example.drink.register.UserResponse
import com.example.drink.register.PassLoginModel
import com.example.drink.register.RegisterPresenter
import com.example.drink.utils.SharedPreferencesUtil
import com.facebook.accountkit.*
import com.facebook.accountkit.ui.AccountKitActivity
import com.facebook.accountkit.ui.AccountKitConfiguration
import com.facebook.accountkit.ui.LoginType
import com.szagurskii.patternedtextwatcher.PatternedTextWatcher
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.register_layout.view.*


private const val REQUEST_CODE = 999

class LoginActivity : AppCompatActivity(), BaseItemViewInterface {

    private lateinit var registerPresenter: RegisterPresenter
    private lateinit var registerDialog: AlertDialog.Builder
    private lateinit var loadingDialog: SpotsDialog

    private var baseItemViewInterface: BaseItemViewInterface = this
    private var phone = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setUpLoading()
        registerPresenter = RegisterPresenter(baseItemViewInterface)

        btnConnectFacebook.setOnClickListener {
            startLoginPage(LoginType.PHONE)
        }

        if (AccountKit.getCurrentAccessToken() != null) {
            setUpAccountKit()
        }
    }

    private fun startLoginPage(loginType: LoginType) {
        when (loginType) {
            LoginType.EMAIL -> {
                val intent = Intent(this@LoginActivity, AccountKitActivity::class.java)
                val configurationBuilder = AccountKitConfiguration.AccountKitConfigurationBuilder(
                    LoginType.EMAIL,
                    AccountKitActivity.ResponseType.TOKEN
                )
                intent.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION, configurationBuilder.build())
                startActivityForResult(intent, REQUEST_CODE)
            }
            LoginType.PHONE -> {
                val intent = Intent(this@LoginActivity, AccountKitActivity::class.java)
                val configurationBuilder = AccountKitConfiguration.AccountKitConfigurationBuilder(
                    LoginType.PHONE,
                    AccountKitActivity.ResponseType.TOKEN
                )
                intent.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION, configurationBuilder.build())
                startActivityForResult(intent, REQUEST_CODE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE) {

            val loginResult = data!!.getParcelableExtra<AccountKitLoginResult>(AccountKitLoginResult.RESULT_KEY)

            when {
                loginResult.error != null -> toast(loginResult.error!!.errorType.message)
                loginResult.wasCancelled() -> toast("Connect Cancelled")
                else -> {

                    if (loginResult.accessToken != null) {
                        setUpAccountKit()
                    }

                }
            }
        }
    }

    override fun onSuccess(data: Any) {
        onHideFullProgressView()

        when (data) {
            is UserResponse -> {
                if (data.status) {
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.putExtra(Common.USER_DATA, data)
                    finish()
                    startActivity(intent)
                } else {
                    showRegisterDialog(phone)
                }
            }
        }
    }

    override fun onShowFullProgressView() {
        loadingDialog.show()
    }

    override fun onHideFullProgressView() {
        loadingDialog.dismiss()
    }

    override fun onDialogNoInternet() {
        onHideFullProgressView()
        log("No Internet")
    }

    override fun onDialogError(message: String) {
        onHideFullProgressView()
        log(message)
    }

    @SuppressLint("InflateParams")
    private fun showRegisterDialog(phone: String) {

        registerDialog = AlertDialog.Builder(this@LoginActivity)
            .setTitle("Register")

        val inflater = this.layoutInflater
        val registerLayout = inflater.inflate(R.layout.register_layout, null)

        val name = registerLayout.editName
        val address = registerLayout.editAddress
        val birthday = registerLayout.editBirthday

        birthday.addTextChangedListener(PatternedTextWatcher("##-##-####"))

        registerLayout.btnRegister.setOnClickListener {
            if (TextUtils.isEmpty(name.text.toString())) {
                toast("Please enter your name")
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(address.text.toString())) {
                toast("Please enter your address")
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(birthday.text.toString())) {
                toast("Please enter your birthday")
                return@setOnClickListener
            }

            registerPresenter.userRegister(
                PassLoginModel(
                    phone,
                    name.text.toString(),
                    address.text.toString(),
                    birthday.text.toString()
                )
            )
        }

        registerDialog.setView(registerLayout)
        registerDialog.show()
    }

    private fun setUpLoading() {
        loadingDialog = SpotsDialog.Builder()
            .setContext(this)
            .setMessage("Please waiting...")
            .setCancelable(false)
            .build() as SpotsDialog
    }

    private fun setUpAccountKit() {
        AccountKit.getCurrentAccount(object : AccountKitCallback<Account> {
            override fun onSuccess(account: Account?) {

                if (account != null) {
                    phone = account.phoneNumber.toString()
                }

                registerPresenter.registerValidate(PassLoginModel(phone, "", "", ""))
            }

            override fun onError(accountKitError: AccountKitError?) {

            }
        })
    }

    private fun toast(s: String) {
        Toast.makeText(this@LoginActivity, s, Toast.LENGTH_LONG).show()
    }

    private fun log(s: String) {
        Log.d("LoginActivityAAA", s)
    }

}
