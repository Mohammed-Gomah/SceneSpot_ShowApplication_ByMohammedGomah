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
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by viewModels()
    private lateinit var auth: FirebaseAuth
    private lateinit var oneTapClient: SignInClient



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = Firebase.auth
        oneTapClient = Identity.getSignInClient(requireContext())

        // ✅ تحقق من وجود الإنترنت
        if (!NetworkUtils.isNetworkAvailable(requireContext())) {
            findNavController().navigate(R.id.noInternetFragment)
            return
        }

        observeLogin()
        initButtons()
        setupLogin()
    }

    private fun initButtons() {
        binding.tvRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        binding.tvResetPasswordButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    private fun setupLogin() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etLoginEmail.text.toString().trim()
            val password = binding.etLoginPassword.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                showToast("Please Fill All Fields")
            } else {
                authViewModel.login(email, password)

            }
        }
    }

    private fun observeLogin() {
        authViewModel.authState.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                if (!user.displayName.isNullOrEmpty()) {
                    Intent(requireContext(), MainActivity::class.java).also { startActivity(it) }
                    requireActivity().finish()
                } else {
                    findNavController().navigate(R.id.action_loginFragment_to_setUsernameFragment)
                }
            }
        }

        authViewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            showToast(message)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
