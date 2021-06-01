package com.example.myapplication.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ItemCategoriesBinding
import com.example.myapplication.model.CategoryModel
import com.example.myapplication.ui.category.CategoryFragmentDirections
import com.example.myapplication.utils.Common

class CategoriesAdapter(
    internal var context: Context,
    private var category: MutableList<CategoryModel>
) : RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesViewHolder {
        return CategoriesViewHolder(
            ItemCategoriesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CategoriesViewHolder, position: Int) {
        holder.bind(category[position])
    }

    override fun getItemViewType(position: Int): Int {
        return if (category.size == 1)
            0
        else {
            if (category.size % Common.NUM_OF_COLUMN == 0)
                Common.DEFAULT_COLUMN_COUNT
            else
                if (position > 1 && position == category.size - 1) Common.DEFAULT_COLUMN_COUNT else Common.FULL_WIDTH_COLUMN
        }

    }

    override fun getItemCount(): Int {
        return category.size
    }

    inner class CategoriesViewHolder(private val binding: ItemCategoriesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(category: CategoryModel) {
            Glide.with(context).load(category.image).into(binding.ivPicCategory)
            binding.tvNameCategory.text = category.name
            binding.root.setOnClickListener {
                Log.d("CategoriesViewHolder", "bind: ${category.name} ${category.menuId}")
                Navigation.findNavController(it).navigate(
                    CategoryFragmentDirections.actionCategoryFragmentToFoodFragment(
                        category
                    )
                )
            }
        }
    }
}