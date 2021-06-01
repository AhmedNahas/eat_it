package com.example.myapplication.ui.registerLogin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterFragment : Fragment(R.layout.fragment_register) {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRegisterBinding.bind(view)
        setupUi()


        auth = Firebase.auth
        val currentUser = auth.currentUser
        if (currentUser != null) {
            //reload()
        }


    }

    private fun setupUi() {
        binding.etEmail.also {
            it.translationX = 800F
            it.alpha = 0F
            it.animate().translationX(0F).alpha(1F).setDuration(800)
                .setStartDelay(300)
                .start()
        }
        binding.etPassword.also {
            it.translationX = 800F
            it.alpha = 0F
            it.animate().translationX(0F).alpha(1F).setDuration(800)
                .setStartDelay(500)
                .start()
        }
        binding.btnRegister.also {
            it.translationX = 800F
            it.alpha = 0F
            it.animate().translationX(0F).alpha(1F).setDuration(800)
                .setStartDelay(700)
                .start()
        }

        binding.btnRegister.setOnClickListener {
            registerUser(
                binding.etEmail.editText?.text.toString(),
                binding.etPassword.editText?.text.toString()
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val TAG = "RegisterFragment"
    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "registerUser: success")
                val user = auth.currentUser
//                updateUI(user)
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "registerUser:failure", it.exception)
                Toast.makeText(
                    requireContext(), "Authentication failed.",
                    Toast.LENGTH_SHORT
                ).show()
//                updateUI(null)
            }
        }
    }
}