package com.example.myapplication.ui.registerLogin

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

const val TAG = "LoginFragment"

class LoginFragment : Fragment(R.layout.fragment_login) {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLoginBinding.bind(view)
        auth = Firebase.auth
        setupUi()
    }

    private fun signUserIn(email: String, password: String) {
        Log.d(TAG, "signInWithEmail: email : $email --> password : $password")
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    findNavController().navigate(R.id.action_loginRegisterFragment_to_homeFragment)
                } else {
                    binding.loading.visibility = View.GONE
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        requireContext(), "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun setupUi() {
        binding.loading.visibility = View.GONE
        binding.etEmailLogin.also {
            it.translationX = 800F
            it.alpha = 0F
            it.animate().translationX(0F).alpha(1F).setDuration(800)
                .setStartDelay(300)
                .start()
        }
        binding.etPasswordLogin.also {
            it.translationX = 800F
            it.alpha = 0F
            it.animate().translationX(0F).alpha(1F).setDuration(800)
                .setStartDelay(500)
                .start()
        }
        binding.btnLogin.also {
            it.translationX = 800F
            it.alpha = 0F
            it.animate().translationX(0F).alpha(1F).setDuration(800)
                .setStartDelay(700)
                .start()
        }

        binding.btnLogin.setOnClickListener {
            binding.loading.visibility = View.VISIBLE
            signUserIn(
                binding.etEmailLogin.editText?.text.toString(),
                binding.etPasswordLogin.editText?.text.toString()
            )
        }

    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            findNavController().navigate(R.id.action_loginRegisterFragment_to_homeFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}