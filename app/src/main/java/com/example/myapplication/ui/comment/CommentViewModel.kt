package com.example.myapplication.ui.comment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.CommentModel

class CommentViewModel : ViewModel() {

    val commentLiveData = MutableLiveData<MutableList<CommentModel>>()


    fun setCommentList(list: MutableList<CommentModel>) {
        commentLiveData.value = list
    }
}