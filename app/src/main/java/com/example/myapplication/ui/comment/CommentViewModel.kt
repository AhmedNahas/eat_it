package com.example.myapplication.ui.comment

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.CommentModel
import com.example.myapplication.utils.Common
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CommentViewModel : ViewModel() {

    val commentLiveData = MutableLiveData<MutableList<CommentModel>>()


    fun setCommentList(list: MutableList<CommentModel>?) {
        commentLiveData.value = list!!
    }

    fun getComments(foodId : String) {
        FirebaseDatabase.getInstance().getReference(Common.COMMENT_REF).child(foodId)
            .orderByChild("commentTimeStamp").limitToLast(100)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val commentList = mutableListOf<CommentModel>()
                    for (value in snapshot.children) {
                        val comment = value.getValue(CommentModel::class.java)
                        commentList.add(comment!!)
                        setCommentList(commentList)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    setCommentList(null)
                }
            })
    }
}