package com.example.myapplication.adapter

import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemCommentBinding
import com.example.myapplication.model.CommentModel

class CommentAdapter(
    internal var context: Context,
    internal var commentList: List<CommentModel>?
) : RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder(
            ItemCommentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(commentList!![position])
    }

    override fun getItemCount(): Int {
        return commentList!!.size
    }

    inner class CommentViewHolder(private val binding: ItemCommentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(comment: CommentModel) {
            binding.tvUserName.text = comment.name
            binding.tvUserComment.text = comment.comment
            binding.rBUserRate.rating = comment.ratingValue
            binding.tvCommentDate.text = DateUtils.getRelativeTimeSpanString(comment.commentTimeStamp!!["timeStamp"].toString().toLong())
        }
    }
}