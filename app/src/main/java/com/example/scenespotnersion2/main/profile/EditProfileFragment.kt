package com.example.scenespotnersion2.main.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.scenespotnersion2.auth.AuthViewModel
import com.example.scenespotnersion2.databinding.FragmentEditProfileBinding


class EditProfileFragment : Fragment() {
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()
    private lateinit var imageResultLauncher: ActivityResultLauncher<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtons()

    }

    private fun initButtons() {
        profileViewModel.user.observe(viewLifecycleOwner){user ->
            binding.tvProfileEmail.text = user?.email
            binding.tvProfileUsername.text =user?.displayName
        }

        imageResultLauncher =
            registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
                uri?.let {
                    authViewModel.uploadProfilePictureToFirebase(it)
                }
            }

        binding.ivEditProfilePicture.setOnClickListener {
            imageResultLauncher.launch("image/*")
        }

        binding.clProfileBackArrow.setOnClickListener {
            this@EditProfileFragment.findNavController().popBackStack()
        }
    }

    override fun onResume() {
        super.onResume()
        profileViewModel.refreshUser()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}