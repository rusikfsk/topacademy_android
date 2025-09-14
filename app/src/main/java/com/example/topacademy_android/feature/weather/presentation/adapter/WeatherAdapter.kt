package com.example.topacademy_android.feature.weather.presentation.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.topacademy_android.R
import com.example.topacademy_android.feature.weather.domain.model.DailyWeather
import com.example.topacademy_android.feature.weather.presentation.WeatherUi
import com.google.android.material.card.MaterialCardView

class WeatherAdapter(
    private val onClick: (DailyWeather) -> Unit
) : ListAdapter<DailyWeather, WeatherAdapter.VH>(DIFF) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_weather, parent, false)
        return VH(v, onClick)
    }

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(getItem(position))

    class VH(
        itemView: View,
        private val onClick: (DailyWeather) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {

        private val icon: ImageView = itemView.findViewById(R.id.icon)
        private val date: TextView = itemView.findViewById(R.id.date)
        private val desc: TextView = itemView.findViewById(R.id.desc)
        private val temp: TextView = itemView.findViewById(R.id.temp)
        private val extra: TextView = itemView.findViewById(R.id.extra)

        fun bind(item: DailyWeather) {
            val ctx = itemView.context

            icon.setImageResource(WeatherUi.iconFor(item.weather))
            date.text = item.date
            desc.text = WeatherUi.labelFor(ctx, item.weather)

            temp.text = ctx.getString(R.string.temp_range_format, item.tMax, item.tMin)
            extra.text = ctx.getString(R.string.wind_format, item.wind)

            val colorRes = WeatherUi.colorFor(item.tMax, item.weather)
            val color = ContextCompat.getColor(ctx, colorRes)
            (itemView as? MaterialCardView)?.setCardBackgroundColor(color)
                ?: run { itemView.backgroundTintList = ColorStateList.valueOf(color) }

            itemView.setOnClickListener { onClick(item) }
        }
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<DailyWeather>() {
            override fun areItemsTheSame(oldItem: DailyWeather, newItem: DailyWeather): Boolean =
                oldItem.date == newItem.date

            override fun areContentsTheSame(oldItem: DailyWeather, newItem: DailyWeather): Boolean =
                oldItem == newItem
        }
    }
}
