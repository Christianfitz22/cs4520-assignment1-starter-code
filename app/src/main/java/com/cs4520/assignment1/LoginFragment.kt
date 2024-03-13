package com.cs4520.assignment1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.cs4520.assignment4.R
import com.cs4520.assignment4.databinding.LoginFragmentBinding

class LoginFragment : Fragment() {
    private var _binding: LoginFragmentBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LoginFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        val usernameInput = binding.usernameInput
        val passwordInput = binding.passwordInput
        val errorText = binding.errorText

        binding.loginButton.setOnClickListener {
            if (usernameInput.text.contentEquals("admin") && passwordInput.text.contentEquals("admin")) {
                errorText.visibility = View.INVISIBLE
                usernameInput.text.clear()
                passwordInput.text.clear()
                findNavController().navigate(R.id.action_loginFragment_to_productListFragment)
            } else {
                errorText.visibility = View.VISIBLE
                Toast.makeText(activity, "Provided login is invalid.", Toast.LENGTH_SHORT).show()
                println("provided login is invalid")
            }
        }

        return view
    }
}