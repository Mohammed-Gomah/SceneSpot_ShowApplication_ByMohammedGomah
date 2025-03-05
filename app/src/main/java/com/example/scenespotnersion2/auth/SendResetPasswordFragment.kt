package com.example.scenespotnersion2.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.scenespotnersion2.databinding.FragmentSendResetPasswordBinding


class SendResetPasswordFragment : Fragment() {
    private var _binding: FragmentSendResetPasswordBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSendResetPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSendResetPasswordButton()
    }

    private fun initSendResetPasswordButton() {
        binding.btnResetPassword.setOnClickListener {
            val email = binding.etSendResetEmail.text.toString().trim()
            if (email.isEmpty()) {
                Toast.makeText(requireContext(), "Enter your email", Toast.LENGTH_SHORT).show()
            } else {
                authViewModel.resetPassword(email)
            }
        }
        authViewModel.resetPasswordStatus.observe(viewLifecycleOwner) { isSuccess ->
            if (isSuccess) {
                Toast.makeText(requireContext(), "link sent successfully , check your email", Toast.LENGTH_SHORT)
                    .show()
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