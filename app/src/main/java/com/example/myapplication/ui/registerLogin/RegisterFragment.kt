package com.example.myapplication.ui.registerLogin

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myapplication.MyApplication
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentRegisterBinding
import com.example.myapplication.model.UserModel
import com.example.myapplication.utils.Common
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

class RegisterFragment : Fragment(R.layout.fragment_register) {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRegisterBinding.bind(view)
        setupUi()
        auth = Firebase.auth
    }

    private fun setupUi() {
        binding.etFirstName.also {
            it.translationX = 800F
            it.alpha = 0F
            it.animate().translationX(0F).alpha(1F).setDuration(800)
                .setStartDelay(100)
                .start()
        }
        binding.etLastName.also {
            it.translationX = 800F
            it.alpha = 0F
            it.animate().translationX(0F).alpha(1F).setDuration(800)
                .setStartDelay(200)
                .start()
        }
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
                Log.d(TAG, "registerUser: success")
                val user = auth.currentUser
                addUserToDB(user!!.uid)
            } else {
                Log.w(TAG, "registerUser:failure", it.exception)
                Toast.makeText(
                    requireContext(), "Authentication failed.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun addUserToDB(uid: String) {
        val newUser = UserModel(
            binding.etAddress.editText?.text.toString(),
            binding.etFirstName.editText?.text.toString()
                    + " " + binding.etLastName.editText?.text.toString(),
            binding.etPhone.editText?.text.toString(),
            uid
        )
        FirebaseDatabase.getInstance().getReference(Common.USER)
            .push().setValue(newUser)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_LONG)
                        .show()
                    MyApplication.getInstance()!!.prefrences.setCurrentUserInfo(Gson().toJson(newUser))
                } else
                    Toast.makeText(requireContext(), it.exception.toString(), Toast.LENGTH_LONG)
                        .show()

            }
    }
}