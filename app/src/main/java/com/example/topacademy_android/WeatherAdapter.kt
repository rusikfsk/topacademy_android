package com.example.topacademy_android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView

class WeatherAdapter(
    private val onClick: (DailyItem) -> Unit
) : ListAdapter<DailyItem, WeatherAdapter.VH>(DIFF) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_weather, parent, false)
        return VH(v, onClick)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

    class VH(
        itemView: View,
        private val onClick: (DailyItem) -> Unit
    ) : RecyclerView.ViewHolder(itemView) {
        private val card = itemView.findViewById<MaterialCardView>(R.id.card)
        private val icon = itemView.findViewById<ImageView>(R.id.icon)
        private val date = itemView.findViewById<TextView>(R.id.date)
        private val desc = itemView.findViewById<TextView>(R.id.desc)
        private val temp = itemView.findViewById<TextView>(R.id.temp)
        private val extra = itemView.findViewById<TextView>(R.id.extra)

        fun bind(item: DailyItem) {
            date.text = item.date
            desc.text = WeatherUi.label(icon.context, item.weather)
            temp.text = icon.context.getString(R.string.temp_range_format, item.tMin, item.tMax)
            extra.text = icon.context.getString(R.string.wind_format, item.wind)
            icon.setImageResource(WeatherUi.iconFor(item.weather))
            val color = WeatherUi.cardColor(item.tMax, item.weather)
            card.setCardBackgroundColor(ContextCompat.getColor(icon.context, color))
            itemView.setOnClickListener { onClick(item) }
        }
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<DailyItem>() {
            override fun areItemsTheSame(oldItem: DailyItem, newItem: DailyItem) = oldItem.date == newItem.date
            override fun areContentsTheSame(oldItem: DailyItem, newItem: DailyItem) = oldItem == newItem
        }
    }
}
