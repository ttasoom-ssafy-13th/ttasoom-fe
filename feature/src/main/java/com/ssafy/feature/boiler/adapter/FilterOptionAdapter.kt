package com.ssafy.feature.boiler.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.feature.databinding.ItemFilterOptionBinding

class FilterOptionAdapter(
    private val options: List<String>,
    private val onOptionSelected: (String) -> Unit
) : RecyclerView.Adapter<FilterOptionAdapter.FilterOptionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterOptionViewHolder {
        val binding = ItemFilterOptionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FilterOptionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilterOptionViewHolder, position: Int) {
        holder.bind(options[position])
    }

    override fun getItemCount(): Int = options.size

    inner class FilterOptionViewHolder(
        private val binding: ItemFilterOptionBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(option: String) {
            binding.tvFilterOption.text = option
            binding.root.setOnClickListener {
                onOptionSelected(option)
            }
        }
    }
} 