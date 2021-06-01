package com.example.myapplication.ui.food

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.adapter.CategoriesAdapter
import com.example.myapplication.adapter.FoodAdapter
import com.example.myapplication.databinding.FragmentFoodBinding
import com.example.myapplication.ui.category.CategoryViewModel
import com.example.myapplication.utils.SpacesItemDecoration

class FoodFragment : Fragment(R.layout.fragment_food) {

    private val mViewModel: CategoryViewModel by viewModels()
    private var _binding: FragmentFoodBinding? = null
    private val binding get() = _binding!!
    private val args: FoodFragmentArgs by navArgs()
    private var layoutAnimationController: LayoutAnimationController? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFoodBinding.bind(view)

        layoutAnimationController =
            AnimationUtils.loadLayoutAnimation(requireContext(), R.anim.layout_item_from_left)
        subscribeToLiveData()

    }

    private fun subscribeToLiveData() {
        mViewModel.getCategoriesListLiveData().observe(viewLifecycleOwner, {
            binding.loading.visibility = View.VISIBLE
            binding.title = args.chosenCategory.name
            val adapter = FoodAdapter(requireContext(), args.chosenCategory.foods)
            binding.rvFood.adapter = adapter
            binding.rvFood.layoutAnimation = layoutAnimationController
            binding.loading.visibility = View.GONE
        })


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "FoodFragment"
    }
}