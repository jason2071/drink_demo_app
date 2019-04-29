package com.example.drink.menu

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.drink.R
import kotlinx.android.synthetic.main.menu_item_layout.view.*

class MenuAdapter(
    private val data: MenuResponse,
    private val listener: SetOnClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface SetOnClickListener {
        fun setOnItemClick(resultMenu: ResultMenu)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menu_item_layout, parent, false)
        return PopularMenuViewHolder(view)
    }

    override fun getItemCount() = if (data.results!!.isEmpty()) 0 else data.results.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as PopularMenuViewHolder
        holder.bind(data, position, listener)
    }

    class PopularMenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(
            data: MenuResponse,
            position: Int,
            listener: SetOnClickListener
        ) {
            val items = data.results!![position]
            itemView.tvMenuName.text = items.menu_name

            Glide
                .with(itemView.imageMenu.context)
                .load(items.menu_image)
                .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(itemView.imageMenu)

            itemView.setOnClickListener {
                listener.setOnItemClick(items)
            }
        }
    }
}