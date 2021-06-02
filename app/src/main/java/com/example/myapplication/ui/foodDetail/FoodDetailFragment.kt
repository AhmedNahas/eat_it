package com.example.myapplication.ui.foodDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentFoodDetailBinding

class FoodDetailFragment : Fragment() {

    private var _binding: FragmentFoodDetailBinding? = null
    private val binding get() = _binding!!
    private var layoutAnimationController: LayoutAnimationController? = null
    private val args: FoodDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater,R.layout.fragment_food_detail,container,false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        subscribeToLiveData()
    }

    private fun setupUI() {
        binding.topAppBar.setNavigationOnClickListener { findNavController().navigateUp() }
        layoutAnimationController =
            AnimationUtils.loadLayoutAnimation(requireContext(), R.anim.layout_item_from_left)
        var quantity = 1
        binding.btnIncrease.setOnClickListener {
            quantity++
            binding.tvFoodQuantity.text = quantity.toString()
        }
        binding.btnDecrease.setOnClickListener {
            if (quantity != 1) quantity--
            binding.tvFoodQuantity.text = quantity.toString()
        }
    }

    private fun subscribeToLiveData() {
        if (args != null)
            binding.lifecycleOwner = this
            binding.food = args.food
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "FoodDetailFragment"
    }
}
