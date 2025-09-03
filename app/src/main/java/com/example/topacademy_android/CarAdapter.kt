package com.example.topacademy_android

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.topacademy_android.databinding.ItemCarBinding

class CarAdapter(private val items: List<Car>) : RecyclerView.Adapter<CarAdapter.VH>() {

    class VH(val vb: ItemCarBinding) : RecyclerView.ViewHolder(vb.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val vb = ItemCarBinding
            .inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        return VH(vb)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val car = items[position]
        val ctx = holder.itemView.context
        holder.vb.title.text =
            ctx.getString(R.string.car_title_format, car.brand, car.model, car.year)
        holder.vb.description.text = car.description
        holder.vb.cost.text =
            ctx.getString(R.string.cost_format, ctx.getString(R.string.currency_symbol), car.cost)
        holder.vb.image.setImageResource(car.imageResId)
        holder.vb.image.contentDescription =
            ctx.getString(R.string.car_image_cd, car.brand, car.model)
    }

    override fun getItemCount() = items.size
}
