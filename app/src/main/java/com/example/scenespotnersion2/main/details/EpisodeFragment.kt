package com.example.scenespotnersion2.main.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scenespotnersion2.databinding.FragmentEpisodeBinding
import com.example.scenespotnersion2.remote.data.data.EpisodeAdapter

class EpisodeFragment : Fragment() {
    private var _binding: FragmentEpisodeBinding? = null
    private val binding get() = _binding!!
    private val detailsViewModel: DetailsViewModel by viewModels()
    private val args: EpisodeFragmentArgs by navArgs()
    private lateinit var episodesAdapter: EpisodeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentEpisodeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val seriesId = args.seriesId
        Log.e(TAG, "onViewCreated: $seriesId", )
        observeEpisodes(seriesId)
        setupAdapters()
        binding.clProfileBackArrow.setOnClickListener {
            this@EpisodeFragment.findNavController().popBackStack()
        }
    }

    private fun observeEpisodes(seriesId: Int) {
        detailsViewModel.fetchShowSeasonEpisodesListById(seriesId)
        detailsViewModel.episodes.observe(viewLifecycleOwner) { episodes ->
            episodesAdapter.setEpisodes(episodes)
        }
    }

    private fun setupAdapters() {
        episodesAdapter = EpisodeAdapter(listOf())
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvEpisodes.apply {
            this.layoutManager = layoutManager
            adapter = episodesAdapter
        }
    }
    companion object{
        private const val TAG = "EpisodeFragment"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}