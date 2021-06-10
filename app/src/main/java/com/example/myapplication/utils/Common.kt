package com.example.myapplication.utils

import java.lang.StringBuilder
import java.math.RoundingMode
import java.text.DecimalFormat

class Common {
    companion object {
        const val PFER_NAME: String = "EatItPrefrences"
        const val USER: String = "Users"
        const val FULL_WIDTH_COLUMN: Int = 1
        const val NUM_OF_COLUMN: Int = 2
        const val DEFAULT_COLUMN_COUNT: Int = 0
        const val POPULAR_REF : String = "MostPopular"
        const val BEST_DEALS : String = "BestDeals"
        const val CATEGORY : String = "Category"
        const val COMMENT_REF : String = "Comments"
        const val FOODS_REF : String = "foods"

        fun formatPrice(price : Double) : String{
            if (price != 0.toDouble()){
                val df = DecimalFormat("#,##0.00")
                df.roundingMode = RoundingMode.HALF_UP
                val finalPrice = StringBuilder(df.format(price)).toString()
                return finalPrice.replace(".",",")
            } else
                return "0,00"
        }
    }

}