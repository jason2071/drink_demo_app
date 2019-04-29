package com.example.drink

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.LinearLayout
import com.daimajia.slider.library.SliderTypes.BaseSliderView
import com.daimajia.slider.library.SliderTypes.TextSliderView
import com.example.drink.banner.BannerPresenter
import com.example.drink.banner.BannerResponse
import com.example.drink.common.Common
import com.example.drink.menu.MenuAdapter
import com.example.drink.menu.MenuPresenter
import com.example.drink.menu.MenuResponse
import com.example.drink.menu.ResultMenu
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BaseItemViewInterface, MenuAdapter.SetOnClickListener {

    private lateinit var bannerPresenter: BannerPresenter
    private lateinit var menuPresenter: MenuPresenter
    private var baseItemViewInterface: BaseItemViewInterface = this

    private lateinit var popularAdapter: MenuAdapter
    private var listener: MenuAdapter.SetOnClickListener = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bannerPresenter = BannerPresenter(baseItemViewInterface)
        bannerPresenter.getBanner()

        menuPresenter = MenuPresenter(baseItemViewInterface)
        menuPresenter.getMenu()

        val bundle = intent.extras
        if (bundle != null) {
            val userData = bundle.get(Common.USER_DATA)

        }
    }

    override fun onSuccess(data: Any) {
        when (data) {
            is BannerResponse -> displayImage(data)
            is MenuResponse -> displayMenu(data)
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

    override fun setOnItemClick(resultMenu: ResultMenu) {
        val intent = Intent(this@MainActivity, DrinkActivity::class.java)
        intent.putExtra(Common.MENU_DATA, resultMenu)
        startActivity(intent)
    }

    private fun displayImage(data: BannerResponse) {
        val bannerMap = HashMap<String, String>()

        if (data.results != null) {

            for (item in data.results) {
                bannerMap.put(item.banner_name!!, item.banner_image!!)
            }

            for (name in bannerMap.keys) {
                val textSlideView = TextSliderView(this@MainActivity)
                textSlideView.description(name)
                    .image(bannerMap.get(name)).scaleType = BaseSliderView.ScaleType.Fit

                slider.addSlider(textSlideView)
            }
        }
    }

    private fun displayMenu(data: MenuResponse) {
        menuRecyclerView.setHasFixedSize(true)
        menuRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayout.HORIZONTAL, false)
        popularAdapter = MenuAdapter(data, listener)
        menuRecyclerView.adapter = popularAdapter
    }

    private fun log(s: String) {
        Log.d("MainActivityAAA", s)
    }

}
