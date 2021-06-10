package com.example.myapplication.ui.comment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.myapplication.adapter.CommentAdapter
import com.example.myapplication.databinding.FragmentCommentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CommentFragment : BottomSheetDialogFragment() {

    private val viewModel: CommentViewModel by viewModels()
    private var _binding: FragmentCommentBinding? = null
    private val binding get() = _binding!!
    private val args: CommentFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCommentBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToLiveData()
    }

    private fun subscribeToLiveData() {
        viewModel.getComments(args.foodId)
        viewModel.commentLiveData.observe(viewLifecycleOwner, {
            binding.loading.visibility = View.GONE
            if (it != null){
                binding.rvComments.setHasFixedSize(true)
                val adapter = CommentAdapter(requireContext(), it)
                binding.rvComments.adapter = adapter
            }else{
                Toast.makeText(requireContext(), "Failed ", Toast.LENGTH_LONG).show()
            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "CommentFragment"
    }

}