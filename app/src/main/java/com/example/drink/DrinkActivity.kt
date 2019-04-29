package com.example.drink

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.drink.common.Common
import com.example.drink.drink.DrinkAdapter
import com.example.drink.drink.DrinkPresenter
import com.example.drink.drink.DrinkResponse
import com.example.drink.drink.ResultDrink
import com.example.drink.menu.ResultMenu
import kotlinx.android.synthetic.main.activity_drink.*
import kotlinx.android.synthetic.main.add_to_cart_layout.view.*

class DrinkActivity : AppCompatActivity(), BaseItemViewInterface, DrinkAdapter.SetOnClickListener {

    private var baseItemViewInterface: BaseItemViewInterface = this
    private lateinit var drinkPresenter: DrinkPresenter
    private var listener: DrinkAdapter.SetOnClickListener = this
    private lateinit var drinkAdapter: DrinkAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drink)

        val bundle = intent.extras
        if (bundle != null) {
            val resultMenu = bundle.getParcelable<ResultMenu>(Common.MENU_DATA)
            if (resultMenu != null) {
                setUpPresenter(resultMenu)
                tvMenuName.text = resultMenu.menu_name
            }
        }
    }

    private fun setUpPresenter(menuData: ResultMenu?) {
        drinkPresenter = DrinkPresenter(baseItemViewInterface)
        drinkPresenter.getDrink(menuData!!.menu_id)
    }

    override fun onSuccess(data: Any) {
        when (data) {
            is DrinkResponse -> displayDrink(data)
        }
    }

    override fun onShowFullProgressView() {
    }

    override fun onHideFullProgressView() {
    }

    override fun onDialogNoInternet() {
    }

    override fun onDialogError(message: String) {
    }

    override fun setOnItemClick(resultDrink: ResultDrink) {
        val builder = AlertDialog.Builder(this@DrinkActivity)
        val inflater = this.layoutInflater
        val itemView = inflater.inflate(R.layout.add_to_cart_layout, null)

        itemView.tvCartProductName.text = resultDrink.drink_name

        Glide
            .with(this@DrinkActivity)
            .load(resultDrink.drink_image)
            .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
            .into(itemView.imageCartProduct)

        builder.setView(itemView)
        builder.setNegativeButton("ADD TO CART") {dialog, which ->
            dialog.dismiss()
        }
        builder.show()
    }

    private fun displayDrink(data: DrinkResponse) {
        drinkRecyclerView.setHasFixedSize(true)
        drinkRecyclerView.layoutManager = GridLayoutManager(this@DrinkActivity, 2)
        drinkAdapter = DrinkAdapter(data, listener)
        drinkRecyclerView.adapter = drinkAdapter
    }
}
