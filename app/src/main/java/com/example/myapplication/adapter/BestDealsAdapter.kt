package com.example.myapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ItemBestDealsBinding
import com.example.myapplication.model.BestDeals

class BestDealsAdapter(
    internal var context: Context,
    internal var bestDeals: MutableList<BestDeals>
) : RecyclerView.Adapter<BestDealsAdapter.BestDealsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestDealsViewHolder {
        return BestDealsViewHolder(
            ItemBestDealsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BestDealsViewHolder, position: Int) {
        holder.bind(bestDeals[position])
    }

    override fun getItemCount(): Int {
        return bestDeals.size
    }

    inner class BestDealsViewHolder(private val binding: ItemBestDealsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(bestDeals : BestDeals) {
            Glide.with(context).load(bestDeals.image).into(binding.ivPicDeals)
            binding.tvNameDeals.text = bestDeals.name
        }
    }
}