package com.example.scenespotnersion2.main.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.core.text.parseAsHtml
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scenespotnersion2.databinding.FragmentDetailsBinding
import com.example.scenespotnersion2.remote.data.SeriesDBItem

class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val detailsViewModel: DetailsViewModel by viewModels()
    private val args: DetailsFragmentArgs by navArgs()
    private lateinit var seasonsAdapter: DetailsSeasonsAdapter
    private lateinit var castAdapter: CastAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val seriesItem = args.seriesItem
        setupDetails(seriesItem)
        initButtons(seriesItem)
        setupAdapters()

        seriesItem.id?.let { observeCastByShowId(it) }
        seriesItem.externals?.imdb?.let { initWebView(it) }
        seriesItem.id?.let { getSeasonsById(it) }

        binding.clDetailsBackArrow.setOnClickListener {
            this@DetailsFragment.findNavController().popBackStack()
        }
    }

    private fun initButtons(series: SeriesDBItem) {
        binding.tvDetailsSeeAllSeasons.setOnClickListener {
            val action = series.id?.let {
                DetailsFragmentDirections.actionDetailsFragmentToSeasonFragment(
                    it
                )
            }
            this@DetailsFragment.findNavController().navigate(action!!)
        }
    }


    private fun setupDetails(seriesItem: SeriesDBItem) {
        binding.apply {
            tvDetailsSeriesName.text = seriesItem.name
            tvDetailsStartTime.text = seriesItem.premiered
            tvDetailsGenres.text = seriesItem.genres?.joinToString(" • ")
            tvDetailsEndTime.text = seriesItem.ended
            ("IMDB ${seriesItem.rating?.average.toString()}").also { tvDetailsRating.text = it }
            tvDetailsDescriptions.text =
                seriesItem.summary?.parseAsHtml(HtmlCompat.FROM_HTML_MODE_LEGACY).toString().trim()
            "PRODUCED BY ${seriesItem.network?.name}".also { network.text = it }
            clDetailsBackArrow.setOnClickListener {
                this@DetailsFragment.findNavController().popBackStack()
            }
        }
    }

    private fun observeCastByShowId(showId: Int) {
        binding.pbCastCrew.visibility = View.VISIBLE
        detailsViewModel.cast.observe(viewLifecycleOwner) { cast ->
            binding.pbCastCrew.visibility = View.GONE
            castAdapter.setCast(cast)
        }
        detailsViewModel.getCastByShowId(showId)
    }

    private fun getSeasonsById(seriesId: Int) {
        binding.pbDetailsSeasons.visibility = View.VISIBLE
        detailsViewModel.seasons.observe(viewLifecycleOwner) { seriesSeasons ->
            binding.pbDetailsSeasons.visibility = View.GONE
            seasonsAdapter.setSeasons(seriesSeasons)
        }
        detailsViewModel.retrieveGetShowSeasonsById(seriesId)
    }


    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView(seriesImdb: String) {
        detailsViewModel.retrieveGetSeriesByImdb(seriesImdb)
        binding.ivDetailsVideoPlaceholder.visibility = View.VISIBLE
        binding.tvDetailsVideoPlaceholder.visibility = View.VISIBLE
        detailsViewModel.seriesDetails.observe(viewLifecycleOwner) { series ->
            binding.ivDetailsVideoPlaceholder.visibility = View.GONE
            binding.tvDetailsVideoPlaceholder.visibility = View.GONE
            binding.wvDetailsTrailer.settings.javaScriptEnabled = true
            binding.wvDetailsTrailer.settings.domStorageEnabled = true
            series?.trailer?.let { binding.wvDetailsTrailer.loadUrl(it) }
        }
    }

    private fun setupAdapters() {
        seasonsAdapter = DetailsSeasonsAdapter(listOf())
        binding.rvDetailsSeasons.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = seasonsAdapter
        }

        castAdapter = CastAdapter(listOf())
        binding.rvCastCrew.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = castAdapter
        }
    }

    companion object{
        private const val TAG = "DetailsFragment"
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}