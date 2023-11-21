package com.example.travelconnect.views

import GenericAdapter
import LocationViewModel
import LocationViewModelFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelconnect.R
import com.example.travelconnect.data.remote.ApiService
import com.example.travelconnect.data.remote.LocationApiClient
import com.example.travelconnect.data.repositories.Repository
import com.example.travelconnect.databinding.FragmentLocationBinding
import com.example.travelconnect.utils.createListWithFirstImage
import com.example.travelconnect.utils.setTransparentStatusBar
import com.squareup.picasso.Picasso
import kotlin.math.exp

class LocationFragment : Fragment() {

    private lateinit var binding: FragmentLocationBinding
    private lateinit var viewModel: LocationViewModel
    private lateinit var locationAdapter: GenericAdapter<String> // String represents the image URL
    private lateinit var exploreAdapter: GenericAdapter<Pair<String, String>> // String represents the image URL

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTransparentStatusBar()

        // Access the arguments to get the name and ID
        val name = arguments?.getString(ARG_NAME)
        val id = arguments?.getString(ARG_ID)

        binding.txtLocationName.text = name

        // Initialize ViewModel
        viewModel = ViewModelProvider(
            this,
            LocationViewModelFactory(Repository(LocationApiClient.apiService)))[LocationViewModel::class.java]

        // Initialize the adapter
        locationAdapter = createLocationAdapter()
        exploreAdapter = createExploreAdapter()

        // Set up to display card_view1 data
        binding.recyclerViewLocationImages.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewLocationImages.adapter = locationAdapter

        // Set up to display explore data
        binding.recyclerViewExploreImages.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewExploreImages.adapter = exploreAdapter

        // Observe the LiveData for location details
        viewModel.locationDetailsLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is LocationViewState.Loading -> {
                    // Show loading indicator
                    // You can add your loading UI logic here
                }

                is LocationViewState.Success -> {
                    val locationDetails = state.locationDetails
                    Picasso.get().load(locationDetails.img[0]).fit().centerCrop().into(binding.bgTitleImg)
                    val imageUrls = locationDetails.img.subList(1, locationDetails.img.size)

                    // Update the adapter with the list of image URLs
                    locationAdapter.setData(imageUrls)

                    // set data for Explore Adapter
                    val restaurants = state.locationDetails.restaurants.createListWithFirstImage(
                        getName = { restaurant -> restaurant.name },
                        getImages = { restaurant -> restaurant.img }
                    )

                    val activities = state.locationDetails.activities.createListWithFirstImage(
                        getName = { activity -> activity.name },
                        getImages = { activity -> activity.img }
                    )
                    exploreAdapter.setData(restaurants + activities)
                }

                is LocationViewState.Error -> {
                    val errorMessage = state.errorMessage
                    // Handle error, show error message, etc.
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                }

                else -> {}
            }
        }

        // Get arguments passed from SearchFragment
        val locationId = arguments?.getString("id")

        // Make API call to get location details
        if (!locationId.isNullOrEmpty()) {
            viewModel.getLocationDetails(locationId)
        } else {
            // Handle case where locationId is null or empty
            Toast.makeText(requireContext(), "Invalid location ID", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val ARG_NAME = "name"
        const val ARG_ID = "id"
    }
    private fun createLocationAdapter(): GenericAdapter<String> {
        return GenericAdapter(
            R.layout.cardview_type2, // Replace with your item layout resource
            onBind = { itemView, imageUrl ->
                val imageView: ImageView = itemView.findViewById(R.id.img_card_background)

                // Load the image using Picasso (you may need to add Picasso as a dependency)
                Picasso.get().load(imageUrl).resize(250, 250)
                    .centerCrop().into(imageView)
            },
            onItemClickListener = object : GenericAdapter.OnItemClickListener<String> {
                override fun onItemClick(item: String) {
                    // Handle the item click here
                    // For example, you can display a Toast message with the item's text
                    Toast.makeText(requireContext(), "Image clicked: $item", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        )
    }

    private fun createExploreAdapter(): GenericAdapter<Pair<String, String>> {
        return GenericAdapter(
            R.layout.cardview_explore, // Replace with your item layout resource
            onBind = { itemView, data ->
                val title: TextView = itemView.findViewById(R.id.explore_title)
                val imageView: ImageView = itemView.findViewById(R.id.img_explore_bg)

                // set explore title
                title.text = data.first
                // Load the image using Picasso (you may need to add Picasso as a dependency)
                Picasso.get().load(data.second).resize(250, 250)
                    .centerCrop().into(imageView)
            },
            onItemClickListener = object : GenericAdapter.OnItemClickListener<Pair<String, String>> {
                override fun onItemClick(item: Pair<String, String>) {
                    // Handle the item click here
                    // For example, you can display a Toast message with the item's text
                    Toast.makeText(requireContext(), "Image clicked: $item", Toast.LENGTH_SHORT)
                        .show()
                }

            }
        )
    }

}