package com.example.myapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ItemPopularCategoriesBinding
import com.example.myapplication.model.PopularCategories

class PopularCategoriesAdapter(
    internal var context: Context,
    internal var popularCategories: MutableList<PopularCategories>
) : RecyclerView.Adapter<PopularCategoriesAdapter.PopularCategoriesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularCategoriesViewHolder {
        return PopularCategoriesViewHolder(
            ItemPopularCategoriesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PopularCategoriesViewHolder, position: Int) {
        holder.bind(popularCategories[position])
    }

    override fun getItemCount(): Int {
        return popularCategories.size
    }

    inner class PopularCategoriesViewHolder(private val binding: ItemPopularCategoriesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(popularCat: PopularCategories) {
            Glide.with(context).load(popularCat.image).into(binding.ivPic)
            binding.tvName.text = popularCat.name
        }
    }
}