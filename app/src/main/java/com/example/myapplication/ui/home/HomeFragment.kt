package com.example.myapplication.ui.home

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.adapter.BestDealsAdapter
import com.example.myapplication.adapter.PopularCategoriesAdapter
import com.example.myapplication.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class HomeFragment : Fragment(R.layout.fragment_home) {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private val mViewModel: HomeViewModel by viewModels()
    private var layoutAnimationController: LayoutAnimationController? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        auth = Firebase.auth
        setHasOptionsMenu(true)
        layoutAnimationController =
            AnimationUtils.loadLayoutAnimation(requireContext(), R.anim.layout_item_from_left)
        subscribeToLiveData()

        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_category -> {
                    navigateToCategories()
                    true
                }
                R.id.action_logOut -> {
                    logUserOut()
                    true
                }
                else -> false
            }
        }

    }

    private fun subscribeToLiveData() {
        binding.loading.visibility = View.VISIBLE
        mViewModel.getPopularCatListLiveData().observe(viewLifecycleOwner, {
            val adapter = PopularCategoriesAdapter(requireContext(), it)
            binding.rvPopularCategories.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.rvPopularCategories.adapter = adapter
            binding.rvPopularCategories.layoutAnimation = layoutAnimationController
            binding.loading.visibility = View.GONE
        })

        mViewModel.getBestDealsLiveData().observe(viewLifecycleOwner, {
            val adapter = BestDealsAdapter(requireContext(), it)
            binding.rvBestDeals.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            binding.rvBestDeals.adapter = adapter
            binding.loading.visibility = View.GONE
        })
    }

    private fun logUserOut() {
        FirebaseAuth.getInstance().signOut()
        findNavController().navigateUp()
    }

    private fun navigateToCategories() {
        findNavController().navigate(R.id.action_homeFragment_to_categoryFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "HomeFragment"
    }

}