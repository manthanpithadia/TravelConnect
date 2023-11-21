package com.example.travelconnect.views

import DBHelper
import GridImageAdapter
import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.travelconnect.databinding.FragmentSearchBinding
import com.example.travelconnect.utils.getLocationNamesWithIds
import com.example.travelconnect.utils.setTransparentStatusBar
import com.example.travelconnect.viewmodels.SearchViewModel

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: SearchViewModel
    private lateinit var dbHelper: DBHelper
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

        dbHelper = DBHelper(requireContext())

        // Example: Get location names with IDs
        val locationNamesWithIds = dbHelper.getLocationNamesWithIds()

        // Set up the AutoCompleteTextView adapter
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_dropdown_item_1line, locationNamesWithIds.map { it.first })
        binding.searchBar.setAdapter(adapter)
        binding.searchBar.threshold = 1

        // You can set other AutoCompleteTextView properties and handle item selection if needed

        binding.searchBar.setOnItemClickListener { _, _, position, _ ->
            // Get the clicked item from the adapter
            val selectedName = binding.searchBar.adapter.getItem(position).toString()

            // Retrieve the ID based on the location name
            val selectedId = dbHelper.getLocationIdByName(selectedName)

            if (selectedId != null) {
                // Navigate to LocationFragment and pass name and ID as arguments
                val args = Bundle().apply {
                    putString("name", selectedName)
                    putString("id", selectedId)
                }

                view?.let {
                    Navigation.findNavController(it)
                        .navigate(com.example.travelconnect.R.id.action_searchFragment_to_locationFragment, args)
                }
            } else {
                Toast.makeText(requireContext(), "Error retrieving location ID", Toast.LENGTH_SHORT).show()
            }
        }


        var gridImageAdapter = GridImageAdapter(requireContext())
        binding.imageGridView.adapter = gridImageAdapter

        setTransparentStatusBar()

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


    private fun navigateToDestinationFragment() {


    }
}