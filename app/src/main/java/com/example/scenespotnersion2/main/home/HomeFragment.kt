package com.example.scenespotnersion2.main.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scenespotnersion2.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var moviesAdapter: MoviesAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapters()
        observeData()
    }

    private fun observeData() {
        binding.pbMovies.visibility = View.VISIBLE
        homeViewModel.movies.observe(viewLifecycleOwner) { movies ->
            binding.pbMovies.visibility = View.GONE
            Log.e("HomeFragment", "setupData: $movies", )
            moviesAdapter.setMovies(movies)
        }
        homeViewModel.getMoviesOrderedByPopularity()
    }

    private fun setupAdapters() {
        moviesAdapter = MoviesAdapter(emptyList())
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvMovies.apply {
            this.layoutManager = layoutManager
            adapter = moviesAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}