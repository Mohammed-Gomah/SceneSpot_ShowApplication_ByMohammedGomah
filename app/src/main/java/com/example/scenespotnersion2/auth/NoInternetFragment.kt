package com.example.scenespotnersion2.auth


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.scenespotnersion2.databinding.FragmentNoInternetBinding

class NoInternetFragment : Fragment() {
    private var _binding: FragmentNoInternetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoInternetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retry Button Click
        binding.retryButton.setOnClickListener {
            if (NetworkUtils.isNetworkAvailable(requireContext())) {
                // ارجع للمكان اللي كنت فيه (لو محتاج تروح لشاشة معينة بدل popBackStack)
                findNavController().popBackStack()
            } else {
                // ممكن تعمل Toast تقول "لسه مفيش إنترنت"
                Toast.makeText(requireContext(), "لسه مفيش إنترنت 😔", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
