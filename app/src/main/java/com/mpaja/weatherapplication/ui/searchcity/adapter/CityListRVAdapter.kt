package com.mpaja.weatherapplication.ui.searchcity.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mpaja.weatherapplication.R
import com.mpaja.weatherapplication.data.repository.model.CityModel
import com.mpaja.weatherapplication.databinding.ItemCityBinding
import com.mpaja.weatherapplication.databinding.ItemCityHeaderBinding
import javax.inject.Inject

class CityListRVAdapter @Inject constructor() :
    ListAdapter<CityModel, RecyclerView.ViewHolder>(CityDiffUtils()) {

    var onItemClickedListener: OnItemClickedListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_HEADER -> {
                val binding = DataBindingUtil.inflate<ItemCityHeaderBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.item_city_header,
                    parent,
                    false
                )
                HeaderViewHolder(binding)
            }
            else -> {
                val binding = DataBindingUtil.inflate<ItemCityBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.item_city, parent, false
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
                    city.id?.let { id -> onItemClickedListener?.onClick(id) }
                }
            }
        }
    }

    fun addCities(cities: List<CityModel>) {
        submitList(cities)
    }

    class CityViewHolder(val binding: ItemCityBinding) : RecyclerView.ViewHolder(binding.root)
    class HeaderViewHolder(val binding: ItemCityHeaderBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnItemClickedListener {
        fun onClick(cityId: Int)
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

class CityDiffUtils : DiffUtil.ItemCallback<CityModel>() {
    override fun areItemsTheSame(oldItem: CityModel, newItem: CityModel) = oldItem == newItem

    override fun areContentsTheSame(oldItem: CityModel, newItem: CityModel) =
        oldItem.id == newItem.id

}