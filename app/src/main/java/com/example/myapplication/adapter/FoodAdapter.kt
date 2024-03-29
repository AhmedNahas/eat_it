package com.example.myapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ItemFoodBinding
import com.example.myapplication.model.FoodModel
import com.example.myapplication.ui.food.FoodFragmentDirections

class FoodAdapter(
    internal var context: Context,
    internal var foodList: List<FoodModel>?
) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        return FoodViewHolder(
            ItemFoodBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(foodList!![position],position)
    }

    override fun getItemCount(): Int {
        return foodList!!.size
    }

    inner class FoodViewHolder(private val binding: ItemFoodBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(food: FoodModel,position: Int) {
            Glide.with(context).load(food.image).into(binding.ivPicFood)
            binding.tvNameMeal.text = food.name
            binding.tvPriceMeal.text = food.price.toString()
            food.key = position.toString()
            binding.root.setOnClickListener {
                Navigation.findNavController(it)
                    .navigate(FoodFragmentDirections.actionFoodFragmentToFoodDetailFragment(food))
            }
        }
    }
}