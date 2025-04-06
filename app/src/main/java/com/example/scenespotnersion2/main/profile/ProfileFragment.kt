package com.example.scenespotnersion2.main.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.scenespotnersion2.R
import com.example.scenespotnersion2.auth.AuthViewModel
import com.example.scenespotnersion2.databinding.FragmentProfileBinding
import com.example.scenespotnersion2.ui.AuthActivity
import com.google.firebase.database.FirebaseDatabase

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val profileViewModel: ProfileViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFirebaseEmailAndUsername()
        initButtons()
    }

    private fun setupFirebaseEmailAndUsername() {
        profileViewModel.user.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                binding.tvProfileEmail.text = user.email ?: "Set username please!"
                binding.tvProfileUsername.text = user.displayName ?: "No username found"

                // تحميل الصورة من Realtime Database
                val userRef = FirebaseDatabase.getInstance().getReference("users").child(user.uid)
                userRef.get().addOnSuccessListener { snapshot ->
                    val photoUrl = snapshot.child("photoUrl").value as? String
                    Glide.with(this)
                        .load(photoUrl) // صورة افتراضية إذا لم يكن هناك صورة
                        .into(binding.ivUserProfileImage)
                }
            }
        }
    }

    private fun initButtons() {
        binding.apply {
            tvLogout.setOnClickListener {
                authViewModel.logout()
                val intent = Intent(requireContext(),AuthActivity::class.java)
                requireContext().startActivity(intent)
            }
            ivLogout.setOnClickListener {
                authViewModel.logout()
                val intent = Intent(requireContext(),AuthActivity::class.java)
                requireContext().startActivity(intent)
            }
        }

        binding.clProfileBackArrow.setOnClickListener {
            this@ProfileFragment.findNavController().popBackStack()
        }

        binding.btnEditProfile.setOnClickListener {
            this@ProfileFragment.findNavController()
                .navigate(R.id.action_profileFragment_to_editProfileFragment)
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