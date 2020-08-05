package com.mpaja.weatherapplication.ui.searchcity.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mpaja.weatherapplication.R
import com.mpaja.weatherapplication.data.repository.model.CityModel
import com.mpaja.weatherapplication.databinding.ItemHistoryBinding
import com.mpaja.weatherapplication.databinding.ItemHistoryHeaderBinding
import javax.inject.Inject

class HistoryListRVAdapter @Inject constructor() :
    ListAdapter<CityModel, RecyclerView.ViewHolder>(CityDiffUtils()) {

    var onHistoryItemClickListener: OnHistoryItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_HEADER -> {
                val binding = DataBindingUtil.inflate<ItemHistoryHeaderBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.item_history_header,
                    parent,
                    false
                )
                HeaderViewHolder(binding)
            }
            else -> {
                val binding = DataBindingUtil.inflate<ItemHistoryBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.item_history, parent, false
                )
                CityViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is CityViewHolder -> {
                val city = getItem(position - 1)
                holder.binding.cityModel = city
                holder.itemView.setOnClickListener {
                    onHistoryItemClickListener?.onClick(city.cityName)
                }
            }
        }
    }

    fun addCities(cities: List<CityModel>) {
        submitList(cities)
    }

    class CityViewHolder(val binding: ItemHistoryBinding) : RecyclerView.ViewHolder(binding.root)
    class HeaderViewHolder(val binding: ItemHistoryHeaderBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnHistoryItemClickListener {
        fun onClick(cityName: String)
    }

    override fun getItemCount(): Int {
        if (super.getItemCount() == 0) return 0
        return super.getItemCount() + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) VIEW_HEADER else VIEW_CITY
    }

    companion object {
        private const val VIEW_HEADER = 0
        private const val VIEW_CITY = 1
    }
}

class HistoryDiffUtils : DiffUtil.ItemCallback<String>() {
    override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem

    override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem.contains(newItem)
}