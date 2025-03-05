package com.example.scenespotnersion2.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.scenespotnersion2.R
import com.example.scenespotnersion2.databinding.FragmentLoginBinding
import com.example.scenespotnersion2.ui.MainActivity

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLogin()
        initButtons()
    }

    private fun initButtons() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etLoginEmail.text.toString().trim()
            val password = binding.etLoginPassword.text.toString()
            if (email.isEmpty() && password.isEmpty()) {
                Toast.makeText(requireContext(), "fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                authViewModel.login(email, password)
            }
        }

        binding.tvRegister.setOnClickListener {
            this@LoginFragment.findNavController()
                .navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.tvResetPasswordButton.setOnClickListener {
            this@LoginFragment.findNavController()
                .navigate(R.id.action_loginFragment_to_sendResetPasswordFragment)
        }

    }

    private fun observeLogin() {
        authViewModel.authState.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                if (user.displayName.isNullOrEmpty()) {
                    parentFragmentManager.beginTransaction()
                        .replace(
                            R.id.fragmentContainerView2,
                            SetUsernameFragment()
                        )
                        .addToBackStack(null)
                        .commit()
                } else {
                    Intent(requireContext(), MainActivity::class.java).also { startActivity(it) }
                    requireActivity().finish()
                }
            }
            authViewModel.errorMessage.observe(viewLifecycleOwner) { message ->
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
