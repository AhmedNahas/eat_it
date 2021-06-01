package com.example.myapplication.ui.registerLogin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.myapplication.R
import com.example.myapplication.adapter.LoginRegisterAdapter
import com.example.myapplication.databinding.FragmentLoginRegisterBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class LoginRegisterFragment : Fragment(R.layout.fragment_login_register) {

    private var _binding: FragmentLoginRegisterBinding? = null
    private lateinit var adapter: LoginRegisterAdapter
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginRegisterBinding.bind(view)
        adapter = LoginRegisterAdapter(this)
        binding.viewPager.adapter = adapter
        binding.tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        binding.tabLayout.also {
            it.translationX = 800F
            it.alpha = 0F
            it.animate().translationX(0F).alpha(1F).setDuration(800)
                .setStartDelay(300)
                .start()
        }
        TabLayoutMediator(
            binding.tabLayout, binding.viewPager
        ) { tab, position -> // Styling each tab here
            when (position) {
                0 -> tab.text = "Login"
                1 -> tab.text = "Register"
            }
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}