package com.example.scenespotnersion2.main.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.core.text.parseAsHtml
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.scenespotnersion2.databinding.FragmentDetailsBinding
import com.example.scenespotnersion2.remote.data.SeriesDBItem

class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val detailsViewModel: DetailsViewModel by viewModels()
    private val args: DetailsFragmentArgs by navArgs()


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
        val seriesImdb = seriesItem.externals?.imdb
        Log.e("DetailsFragment", "onViewCreated:$seriesImdb ")
        seriesImdb?.let { initWebView(it) }
    }

    private fun setupDetails(seriesItem: SeriesDBItem) {
        binding.apply {
            tvDetailsSeriesName.text = seriesItem.name
            tvDetailsStartTime.text = seriesItem.premiered
            tvDetailsGenres.text = seriesItem.genres?.joinToString(" • ")
            tvDetailsEndTime.text = seriesItem.ended
            tvDetailsRating.text = seriesItem.rating?.average.toString()
            tvDetailsDescriptions.text =
                seriesItem.summary?.parseAsHtml(HtmlCompat.FROM_HTML_MODE_LEGACY).toString().trim()
            "PRODUCED BY ${seriesItem.network?.name}".also { network.text = it }
            clDetailsBackArrow.setOnClickListener {
                this@DetailsFragment.findNavController().popBackStack()
            }
                       
        }

    }


    
    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView(seriesImdb: String) {
        detailsViewModel.retrieveGetSeriesByImdb(seriesImdb)
        detailsViewModel.seriesDetails.observe(viewLifecycleOwner) { series ->
            binding.wvDetailsTrailer.settings.javaScriptEnabled = true
            binding.wvDetailsTrailer.settings.domStorageEnabled = true
            series?.trailer?.let { binding.wvDetailsTrailer.loadUrl(it) }
            Log.e("DetailsFragment", "initWebView: ${series?.trailer}")
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