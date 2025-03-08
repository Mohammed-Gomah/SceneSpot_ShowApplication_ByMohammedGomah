package com.example.scenespotnersion2.main.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scenespotnersion2.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val searchViewModel: SearchViewModel by viewModels()
    private lateinit var searchAdapter: SearchAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapters()
        setupSearch()
    }

    private fun observeSearch(showName: String) {
        searchViewModel.searchSeriesByName(showName)
        binding.tvTextWaiting.visibility = View.VISIBLE
        binding.cameraImageWaiting.visibility = View.VISIBLE
        searchViewModel.seriesList.observe(viewLifecycleOwner) { searchList ->
            binding.tvTextWaiting.visibility = View.GONE
            binding.cameraImageWaiting.visibility = View.GONE
            searchAdapter.setSearchList(searchList)
        }
    }

    private fun setupSearch() {
        binding.svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrEmpty()) {
                    observeSearch(newText)
                } else {
                    binding.tvTextWaiting.visibility = View.VISIBLE
                    binding.cameraImageWaiting.visibility = View.VISIBLE
                }
                return true
            }
        })
    }

    private fun setupAdapters() {
        searchAdapter = SearchAdapter(listOf())
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvSearch.apply {
            this.layoutManager = layoutManager
            adapter = searchAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}