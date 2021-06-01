package com.example.myapplication.ui.category

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapter.CategoriesAdapter
import com.example.myapplication.databinding.FragmentCategoryBinding
import com.example.myapplication.utils.Common
import com.example.myapplication.utils.SpacesItemDecoration

class CategoryFragment : Fragment(R.layout.fragment_category) {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!
    private val mViewModel: SharedViewModel by activityViewModels()
    private var layoutAnimationController: LayoutAnimationController? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCategoryBinding.bind(view)
        binding.topAppBar.setNavigationOnClickListener { findNavController().navigateUp() }

        layoutAnimationController =
            AnimationUtils.loadLayoutAnimation(requireContext(), R.anim.layout_item_from_left)
        mViewModel.loadPopularList()
        subscribeToLiveData()
    }

    private fun subscribeToLiveData() {
        binding.loading.visibility = View.VISIBLE
        mViewModel.getCategoriesListLiveData().observe(viewLifecycleOwner, {
            val adapter = CategoriesAdapter(requireContext(), it)
            binding.rvCategories.layoutManager = GridLayoutManager(requireContext(), 2).apply {
                orientation = RecyclerView.VERTICAL
                spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return if (adapter != null) {
                            when (adapter.getItemViewType(position)) {
                                1 -> Common.FULL_WIDTH_COLUMN
                                0 -> Common.NUM_OF_COLUMN
                                else -> -1
                            }
                        } else -1
                    }
                }
            }
            binding.rvCategories.adapter = adapter
            binding.rvCategories.layoutAnimation = layoutAnimationController
            binding.rvCategories.addItemDecoration(SpacesItemDecoration(8))
            binding.loading.visibility = View.GONE
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "CategoryFragment"
    }
}