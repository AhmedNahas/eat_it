package com.example.myapplication.model

import java.io.Serializable

class FoodModel : Serializable{
    var id: String? = null
    var name: String? = null
    var image: String? = null
    var description : String? = null
    var price : Long = 0
    var addon : List<AddonModel>? = null
    var size : List<SizeModel>? = null


}