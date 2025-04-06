import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.activity.result.IntentSenderRequest
import com.example.scenespotnersion2.R
import com.example.scenespotnersion2.auth.AuthViewModel
import com.example.scenespotnersion2.databinding.FragmentLoginBinding
import com.example.scenespotnersion2.ui.MainActivity
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
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

        observeLogin()
        initButtons()
    }

    private fun initButtons() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etLoginEmail.text.toString().trim()
            val password = binding.etLoginPassword.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "يرجى ملء جميع الحقول", Toast.LENGTH_SHORT).show()
            } else {
                authViewModel.login(email, password)
            }
        }

        binding.tvRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.tvResetPasswordButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_sendResetPasswordFragment)
        }


    }





    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        navigateToNextScreen(currentUser?.displayName)
    }

    private fun observeLogin() {
        authViewModel.authState.observe(viewLifecycleOwner) { user ->
            user?.let {
                navigateToNextScreen(it.displayName)
            }
        }

        authViewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToNextScreen(displayName: String?) {
        if (displayName.isNullOrEmpty()) {
            findNavController().navigate(R.id.action_loginFragment_to_setUsernameFragment)
        } else {
            startActivity(Intent(requireContext(), MainActivity::class.java))
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
