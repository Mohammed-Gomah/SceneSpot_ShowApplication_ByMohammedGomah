package com.example.scenespotnersion2.main.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scenespotnersion2.databinding.FragmentFavouritesBinding

class FavouritesFragment : Fragment() {
    private var _binding: FragmentFavouritesBinding? = null
    private val binding get() = _binding!!
    private val favouritesViewModel: FavouritesViewModel by viewModels()
    private lateinit var favouritesAdapter: FavouritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapters()
        observeFavourites()
        setupSwipeRefreshLayout()

        binding.ivBackEditProfileBackArrow.setOnClickListener {
            this@FavouritesFragment.findNavController().popBackStack()
        }
    }

    private fun setupSwipeRefreshLayout(){
        binding.srlFavourites.setOnRefreshListener {
            favouritesViewModel.getFavourites()
        }
    }

    private fun observeFavourites() {
        favouritesViewModel.favouritesList.observe(viewLifecycleOwner) { favourites ->
            favouritesAdapter.setFavourites(favourites)
            binding.srlFavourites.isRefreshing = false
        }
        favouritesViewModel.getFavourites()
    }

    private fun setupAdapters() {
        favouritesAdapter = FavouritesAdapter(emptyList(),favouritesViewModel)
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvFavourites.apply {
            this.layoutManager = layoutManager
            adapter = favouritesAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
