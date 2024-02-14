package com.cs4520.assignment1

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController

class LoginFragment : Fragment(R.layout.login_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState);

        val usernameInput = view.findViewById<EditText>(R.id.usernameInput)
        val passwordInput = view.findViewById<EditText>(R.id.passwordInput)
        val errorText = view.findViewById<TextView>(R.id.errorText)

        view.findViewById<Button>(R.id.loginButton).setOnClickListener(View.OnClickListener {
            if (usernameInput.text.contentEquals("admin") && passwordInput.text.contentEquals("admin")) {
                errorText.visibility = View.INVISIBLE
                usernameInput.text.clear();
                passwordInput.text.clear();
                findNavController().navigate(R.id.action_loginFragment_to_productListFragment)
            } else {
                errorText.visibility = View.VISIBLE
            }
        })
    }
}