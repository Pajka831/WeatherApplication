package com.mpaja.weatherapplication.ui.cityweather.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mpaja.weatherapplication.R
import com.mpaja.weatherapplication.data.repository.model.ForecastModel
import com.mpaja.weatherapplication.databinding.ItemForecastBinding
import com.mpaja.weatherapplication.databinding.ItemForecastHeaderBinding
import kotlinx.android.synthetic.main.fragment_city_weather.*
import javax.inject.Inject

class ForecastRvAdapter @Inject constructor() :
    ListAdapter<ForecastModel, RecyclerView.ViewHolder>(ForecastDiffUtils()) {

    var onItemClickedListener: OnItemClickedListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_HEADER -> {
                val binding = DataBindingUtil.inflate<ItemForecastHeaderBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.item_forecast_header,
                    parent,
                    false
                )
                HeaderViewHolder(binding)
            }
            else -> {
                val binding = DataBindingUtil.inflate<ItemForecastBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.item_forecast, parent, false
                )
                ForecastViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ForecastViewHolder -> {
                val forecast = getItem(position - 1)
                holder.binding.forecastModel = forecast
                Glide.with(holder.itemView)
                    .load("https://openweathermap.org/img/wn/${forecast.iconCode}@2x.png")
                    .into(holder.binding.imageIcon)
            }
        }
    }

    fun addForecast(forecast: List<ForecastModel>) {
        submitList(forecast)
    }

    class ForecastViewHolder(val binding: ItemForecastBinding) : RecyclerView.ViewHolder(binding.root){

    }
    class HeaderViewHolder(val binding: ItemForecastHeaderBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnItemClickedListener {
        fun onClick(cityId: Int)
    }

    override fun getItemCount(): Int {
        if (super.getItemCount() == 0) return 0
        return super.getItemCount() + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) VIEW_HEADER else VIEW_FORECAST
    }

    companion object {
        private const val VIEW_HEADER = 0
        private const val VIEW_FORECAST = 1
    }
}

class ForecastDiffUtils : DiffUtil.ItemCallback<ForecastModel>() {

    override fun areItemsTheSame(oldItem: ForecastModel, newItem: ForecastModel): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: ForecastModel, newItem: ForecastModel): Boolean {
        return oldItem.dtMillis == newItem.dtMillis
    }

}