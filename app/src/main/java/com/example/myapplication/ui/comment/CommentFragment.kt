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
import com.example.myapplication.model.CommentModel
import com.example.myapplication.utils.Common
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

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
        _binding = FragmentCommentBinding.inflate(layoutInflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getComments()
        subscribeToLiveData()
    }

    private fun subscribeToLiveData() {
        viewModel.commentLiveData.observe(viewLifecycleOwner, {
            binding.loading.visibility = View.GONE
            binding.rvComments.setHasFixedSize(true)
            val adapter = CommentAdapter(requireContext(), it)
            binding.rvComments.adapter = adapter
        })
    }

    private fun getComments() {
        FirebaseDatabase.getInstance().getReference(Common.COMMENT_REF).child(args.foodId)
            .orderByChild("commentTimeStamp").limitToLast(100)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val commentList = mutableListOf<CommentModel>()
                    for (value in snapshot.children) {
                        val comment = value.getValue(CommentModel::class.java)
                        commentList.add(comment!!)
                        viewModel.setCommentList(commentList)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireContext(), error.message, Toast.LENGTH_LONG).show()
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