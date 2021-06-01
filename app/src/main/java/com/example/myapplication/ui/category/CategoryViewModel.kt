package com.example.myapplication.ui.category

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.CategoryModel
import com.example.myapplication.utils.Common
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CategoryViewModel : ViewModel() {

    val categoriesLiveData = MutableLiveData<ArrayList<CategoryModel>>()


    private fun loadPopularList() {
        val tempList = ArrayList<CategoryModel>()
        val reference = FirebaseDatabase.getInstance().getReference(Common.CATEGORY)
        reference.orderByKey().addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (data in snapshot.children){
                    val value = data.getValue(CategoryModel::class.java)
                    val key = data.key
                    value!!.menuId = key
                    tempList.add(value!!)
                }
                Log.d(CategoryFragment.TAG, "onDataChange: categoryList ${tempList.size}")
                categoriesLiveData.value = tempList
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(CategoryFragment.TAG, "onCancelled: $error")
            }
        })
    }


    fun getCategoriesListLiveData(): MutableLiveData<ArrayList<CategoryModel>> {
        return categoriesLiveData
    }

    init {
        loadPopularList()
    }

}