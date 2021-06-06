package com.example.myapplication.ui.foodDetail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.CommentModel
import com.example.myapplication.model.FoodModel

class FoodDetailViewModel : ViewModel() {

    val comment = MutableLiveData<CommentModel>()
    val food = MutableLiveData<FoodModel>()

    fun setComment(comment: CommentModel) {
        this.comment.value = comment
    }

    fun getCommentLiveData(): MutableLiveData<CommentModel> {
        return comment
    }

    fun getFoodLiveData(): MutableLiveData<FoodModel> {
        return food
    }

    fun setFood(food: FoodModel) {
        this.food.value = food
    }

}
