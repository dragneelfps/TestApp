package com.example.testapp.ui.adaptors

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.R
import com.example.testapp.data.CovidItem
import com.example.testapp.data.CovidItem.DistrictItem
import com.example.testapp.data.CovidItem.StateItem
import com.example.testapp.databinding.ItemDisctrictBinding
import com.example.testapp.databinding.ItemStateBinding
import com.example.testapp.ui.adaptors.CovidListViewHolder.DistrictItemViewHolder
import com.example.testapp.ui.adaptors.CovidListViewHolder.StateItemViewHolder

class CovidListAdaptor :
    RecyclerView.Adapter<CovidListViewHolder>() {

    private var covidData: List<CovidItem> = emptyList()
    private var initialData: List<CovidItem> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CovidListViewHolder {
        return when (viewType) {
            0 -> {
                val binding: ItemStateBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_state,
                    parent,
                    false
                )
                StateItemViewHolder(binding)
            }
            1 -> {
                val binding: ItemDisctrictBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_disctrict,
                    parent,
                    false
                )
                DistrictItemViewHolder(binding)
            }
            else -> throw IllegalStateException("Should not happen")
        }
    }

    override fun onBindViewHolder(holder: CovidListViewHolder, position: Int) {
        val covidItem = covidData[position]
        holder.bind(covidItem)
    }

    override fun getItemCount(): Int = covidData.size

    override fun getItemViewType(position: Int): Int {
        return when (covidData[position]) {
            is StateItem -> 0
            is DistrictItem -> 1
        }
    }

    fun load(newCovidData: List<CovidItem>) {
        initialData = newCovidData
        covidData = initialData
        notifyDataSetChanged()
    }

    fun filter(predicate: (CovidItem) -> Boolean) {
        covidData = initialData.filter(predicate)
        notifyDataSetChanged()
    }
}


sealed class CovidListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    open fun bind(covidItem: CovidItem) {}

    class StateItemViewHolder(private val binding: ItemStateBinding) :
        CovidListViewHolder(binding.root) {
        override fun bind(covidItem: CovidItem) {
            val stateItem = covidItem as StateItem
            binding.stateName = stateItem.name
        }
    }

    class DistrictItemViewHolder(private val binding: ItemDisctrictBinding) :
        CovidListViewHolder(binding.root) {
        override fun bind(covidItem: CovidItem) {
            val districtItem = covidItem as DistrictItem
            binding.district = districtItem
        }
    }
}