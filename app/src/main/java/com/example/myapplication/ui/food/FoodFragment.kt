package com.example.myapplication.ui.food

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.adapter.FoodAdapter
import com.example.myapplication.databinding.FragmentFoodBinding
import com.example.myapplication.ui.category.SharedViewModel

class FoodFragment : Fragment(R.layout.fragment_food) {

    private val mViewModel: SharedViewModel by activityViewModels()
    private var _binding: FragmentFoodBinding? = null
    private val binding get() = _binding!!
    private val args: FoodFragmentArgs by navArgs()
    private var layoutAnimationController: LayoutAnimationController? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFoodBinding.bind(view)
        binding.topAppBar.setNavigationOnClickListener { findNavController().navigateUp() }

        layoutAnimationController =
            AnimationUtils.loadLayoutAnimation(requireContext(), R.anim.layout_item_from_left)
        subscribeToLiveData()

    }

    private fun subscribeToLiveData() {
        binding.loading.visibility = View.VISIBLE
        mViewModel.getCategoriesListLiveData().observe(viewLifecycleOwner, {
            if (!it.isNullOrEmpty()){
                binding.title = args.chosenCategory.name
                args.chosenCategory.foods?.forEach { it.menuId = args.chosenCategory.menuId }
                val adapter = FoodAdapter(requireContext(), args.chosenCategory.foods)
                binding.rvFood.adapter = adapter
                binding.rvFood.layoutAnimation = layoutAnimationController
            }else{
                Toast.makeText(requireContext(),"Connection Failed !",Toast.LENGTH_LONG).show()
            }
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