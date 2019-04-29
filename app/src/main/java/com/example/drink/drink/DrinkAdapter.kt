package com.example.drink.drink

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.drink.R
import kotlinx.android.synthetic.main.drink_item_layout.view.*

class DrinkAdapter(private val data: DrinkResponse, private val listener: SetOnClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface SetOnClickListener {
        fun setOnItemClick(resultDrink: ResultDrink)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.drink_item_layout, parent, false)
        return DrinkViewHolder(view)
    }

    override fun getItemCount() = if (data.results!!.isEmpty()) 0 else data.results.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as DrinkViewHolder
        holder.bind(data, position, listener)
    }

    class DrinkViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            data: DrinkResponse,
            position: Int,
            listener: SetOnClickListener
        ) {
            val items = data.results!![position]
            itemView.tvDrinkName.text = items.drink_name
            itemView.tvDrinkPrice.text = StringBuilder("$").append(items.drink_price)
            Glide
                .with(itemView.imageDrink.context)
                .load(items.drink_image)
                .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(itemView.imageDrink)

            itemView.btnAddCart.setOnClickListener { listener.setOnItemClick(items) }
        }

    }
}