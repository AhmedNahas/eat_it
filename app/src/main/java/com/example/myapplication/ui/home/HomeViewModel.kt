package com.example.myapplication.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.BestDeals
import com.example.myapplication.model.PopularCategories
import com.example.myapplication.utils.Common
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeViewModel : ViewModel() {

    val popularListLiveData = MutableLiveData<ArrayList<PopularCategories>>()
    val bestDealsListLiveData = MutableLiveData<ArrayList<BestDeals>>()


    private fun loadPopularList() {
        val tempList = ArrayList<PopularCategories>()
        val reference = FirebaseDatabase.getInstance().getReference(Common.POPULAR_REF)
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (data in snapshot.children){
                    val value = data.getValue<PopularCategories>(PopularCategories::class.java)
                    tempList.add(value!!)
                }
                Log.d(HomeFragment.TAG, "onDataChange: popularList ${tempList.size}")
                popularListLiveData.value = tempList
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(HomeFragment.TAG, "onCancelled: $error")
            }
        })
    }

    private fun loadBestDeals() {
        val tempList = ArrayList<BestDeals>()
        val reference = FirebaseDatabase.getInstance().getReference(Common.BEST_DEALS)
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (data in snapshot.children){
                    val value = data.getValue(BestDeals::class.java)
                    tempList.add(value!!)
                }
                Log.d(HomeFragment.TAG, "onDataChange: bestDeals ${tempList.size}")
                bestDealsListLiveData.value = tempList
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(HomeFragment.TAG, "onCancelled: $error")
            }
        })
    }

    fun getPopularCatListLiveData(): MutableLiveData<ArrayList<PopularCategories>> {
        return popularListLiveData
    }

    fun getBestDealsLiveData(): MutableLiveData<ArrayList<BestDeals>> {
        return bestDealsListLiveData
    }


    init {
        loadPopularList()
        loadBestDeals()
    }

}