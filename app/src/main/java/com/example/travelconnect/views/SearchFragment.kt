package com.example.travelconnect.views

import GridImageAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.travelconnect.databinding.FragmentSearchBinding
import com.example.travelconnect.viewmodels.SearchViewModel

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[SearchViewModel::class.java]

        var gridImageAdapter = GridImageAdapter(requireContext())
        binding.imageGridView.adapter = gridImageAdapter


        // Observe LiveData for image list
        viewModel.getImageList().observe(viewLifecycleOwner) { imageList ->
            gridImageAdapter.setData(imageList)
        }

        // Observe LiveData for errors
        viewModel.getError().observe(viewLifecycleOwner) {
            // Handle errors, e.g., show an error message to the user
        }

        // Fetch images
        viewModel.fetchImages()

    }
}