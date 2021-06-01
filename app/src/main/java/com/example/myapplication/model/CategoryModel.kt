package com.example.myapplication.model

import java.io.Serializable

class CategoryModel : Serializable{
    var menuId: String? = null
    var name: String? = null
    var image: String? = null
    var foods : List<FoodModel>? = null


}