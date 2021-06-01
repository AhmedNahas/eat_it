package com.example.myapplication.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapplication.ui.registerLogin.LoginFragment
import com.example.myapplication.ui.registerLogin.RegisterFragment

class LoginRegisterAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment = Fragment()
        when (position) {
            0 -> LoginFragment().also { fragment = it }
            1 -> RegisterFragment().also { fragment = it }
        }
        return fragment
    }
}
