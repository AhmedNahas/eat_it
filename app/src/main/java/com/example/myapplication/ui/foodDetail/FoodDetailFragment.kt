package com.example.myapplication.ui.foodDetail

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentFoodDetailBinding

class FoodDetailFragment : Fragment(R.layout.fragment_food_detail,) {

    private val mViewModel: FoodDetailViewModel by viewModels()
    private var _binding: FragmentFoodDetailBinding? = null
    private val binding get() = _binding!!
    private var layoutAnimationController: LayoutAnimationController? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFoodDetailBinding.bind(view)
        binding.topAppBar.setNavigationOnClickListener { findNavController().navigateUp() }
        layoutAnimationController =
            AnimationUtils.loadLayoutAnimation(requireContext(), R.anim.layout_item_from_left)
        subscribeToLiveData()

    }

    private fun subscribeToLiveData() {
  /*      binding.loading.visibility = View.VISIBLE
        mViewModel.getCategoriesListLiveData().observe(viewLifecycleOwner, {
            if (!it.isNullOrEmpty()){
                binding.title = args.chosenCategory.name
                val adapter = FoodAdapter(requireContext(), args.chosenCategory.foods)
                binding.rvFood.adapter = adapter
                binding.rvFood.layoutAnimation = layoutAnimationController
            }else{
                Toast.makeText(requireContext(),"Connection Failed !", Toast.LENGTH_LONG).show()
            }
            binding.loading.visibility = View.GONE
        })
*/

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "FoodDetailFragment"
    }
}
