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
import com.example.scenespotnersion2.databinding.FragmentRegisterBinding
import com.example.scenespotnersion2.ui.MainActivity


class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtons()
        observeRegister()
    }

    private fun initButtons() {
        binding.btnRegister.setOnClickListener {
            val email = binding.etRegisterEmail.text.toString().trim()
            val password = binding.etRegisterPassword.text.toString()
            if (email.isEmpty() && password.isEmpty()) {
                Toast.makeText(requireContext(), "fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                authViewModel.register(email, password)
            }
        }

        binding.tvLogin.setOnClickListener {
            this@RegisterFragment.findNavController()
                .navigate(R.id.action_registerFragment_to_loginFragment)
        }

    }

    private fun observeRegister() {
        authViewModel.authState.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                startActivity(Intent(requireContext(), MainActivity::class.java))
                requireActivity().finish()
            }
        }

        authViewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}