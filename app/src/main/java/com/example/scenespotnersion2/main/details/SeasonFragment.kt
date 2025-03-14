package com.example.scenespotnersion2.main.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.scenespotnersion2.databinding.FragmentSeasonBinding

class SeasonFragment : Fragment() {
    private var _binding: FragmentSeasonBinding? = null
    private val binding get() = _binding!!
    private val args: SeasonFragmentArgs by navArgs()
    private val detailsViewModel: DetailsViewModel by viewModels()
    private lateinit var seasonsAdapter: SeasonsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSeasonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val seasons = args.seriesId
        setupAdapters()
        getSeasons(seasons)

        binding.clProfileBackArrow.setOnClickListener {
            this@SeasonFragment.findNavController().popBackStack()
        }
    }

    private fun getSeasons(seriesId: Int) {
        detailsViewModel.seasons.observe(viewLifecycleOwner) { seasons ->
            seasonsAdapter.setSeasonsList(seasons)
        }
        detailsViewModel.retrieveGetShowSeasonsById(seriesId)
    }

    private fun setupAdapters() {
        seasonsAdapter = SeasonsAdapter(emptyList())
        val layoutManager =
            GridLayoutManager(requireContext(), 2)
        binding.rvSeasons.apply {
            this.layoutManager = layoutManager
            adapter = seasonsAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}