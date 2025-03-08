package com.example.scenespotnersion2.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scenespotnersion2.R
import com.example.scenespotnersion2.databinding.FragmentHomeBinding
import com.example.scenespotnersion2.main.profile.ProfileViewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()
    private lateinit var moviesAdapter: MoviesAdapter
    private lateinit var seriesAdapter: SeriesAdapter
    private lateinit var seriesDescriptionAdapter: SeriesDescriptionAdapter


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
        initUsername()
        init()
    }

    private fun observeData() {
        binding.pbMovies.visibility = View.VISIBLE
        homeViewModel.movies.observe(viewLifecycleOwner) { movies ->
            binding.pbMovies.visibility = View.GONE
            moviesAdapter.setMovies(movies)
        }
        homeViewModel.fetchAllMoviesIMDB()

        binding.pbSeries.visibility = View.VISIBLE
        homeViewModel.series.observe(viewLifecycleOwner) { series ->
            binding.pbSeries.visibility = View.GONE
            seriesAdapter.setSeries(series)
        }
        homeViewModel.fetchAllSeries()

        binding.pbDescription.visibility = View.VISIBLE
        homeViewModel.seriesDescription.observe(viewLifecycleOwner){series->
            binding.pbDescription.visibility = View.GONE
            seriesDescriptionAdapter.setSeries(series)
        }
        homeViewModel.fetchAllSeriesWithDescription()
    }

    private fun setupAdapters() {
        moviesAdapter = MoviesAdapter(emptyList())
        val moviesLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvMovies.apply {
            this.layoutManager = moviesLayoutManager
            adapter = moviesAdapter
        }

        seriesAdapter = SeriesAdapter(emptyList())
        val seriesLayoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvSeries.apply {
            this.layoutManager = seriesLayoutManager
            adapter = seriesAdapter
        }

        seriesDescriptionAdapter = SeriesDescriptionAdapter(emptyList())
        val seriesDescriptionLayoutManager = LinearLayoutManager(requireContext())
        binding.rvDescription.apply {
            this.layoutManager = seriesDescriptionLayoutManager
            adapter = seriesDescriptionAdapter
        }

    }

    private fun initUsername() {
        profileViewModel.user.observe(viewLifecycleOwner) { user ->
            binding.tvHomeUsername.text = user?.displayName ?: ""
        }
    }

    private fun init() {
        binding.homeSearchView.setOnClickListener {
            this@HomeFragment.findNavController()
                .navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}