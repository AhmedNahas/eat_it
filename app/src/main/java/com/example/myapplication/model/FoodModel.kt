package com.example.myapplication.model

import java.io.Serializable

data class FoodModel(
    var id: String? = null,
    var key: String? = null,
    var menuId: String? = null,
    var name: String? = null,
    var image: String? = null,
    var description: String? = null,
    var price: Long = 0,
    var addon: List<AddonModel>? = null,
    var size: List<SizeModel>? = null,
    var ratingValue: Double = 0.0,
    var ratingCount: Long = 0
) : Serializable