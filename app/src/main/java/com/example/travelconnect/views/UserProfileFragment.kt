package com.example.travelconnect.views

import GenericAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelconnect.R
import com.example.travelconnect.data.model.Trip
import com.example.travelconnect.data.model.UserProfile
import com.example.travelconnect.data.remote.LocationApiClient
import com.example.travelconnect.data.repositories.Repository
import com.example.travelconnect.databinding.FragmentSearchBinding
import com.example.travelconnect.databinding.FragmentUserProfileBinding
import com.example.travelconnect.utils.createListWithFirstImage
import com.example.travelconnect.utils.getFromSharedPreferences
import com.example.travelconnect.utils.setTransparentStatusBar
import com.example.travelconnect.viewmodels.SearchViewModel
import com.example.travelconnect.viewmodels.UserProfileViewModel
import com.example.travelconnect.viewmodels.UserProfileViewModelFactory
import com.example.travelconnect.viewmodels.UserProfileViewState
import com.squareup.picasso.Picasso

class UserProfileFragment : Fragment() {

    private lateinit var binding: FragmentUserProfileBinding
    private lateinit var viewModel: UserProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTransparentStatusBar()
        val (token, avatarString) = getFromSharedPreferences()
        //loadProfileImage(avatarString)
        viewModel = ViewModelProvider(
            this,
            UserProfileViewModelFactory(Repository(LocationApiClient.apiService)))[UserProfileViewModel::class.java]
        binding.recyclerViewTripHistory.layoutManager = LinearLayoutManager(requireContext())

        viewModel.userProfileLiveData.observe(viewLifecycleOwner) {
            updateUI(it)
        }

        binding.btnLogout.setOnClickListener {
            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            requireActivity().finish()
        }
       /* // Observe the LiveData for location details
        viewModel.locationDetailsLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is UserProfileViewState.Loading -> {
                    Log.d("Log","Loading")
                }

                is UserProfileViewState.Success -> {
                    Log.d("Log","Success ${state.userDetails.avatar}")
                        //updateUI(state.userDetails)
                }

                is UserProfileViewState.Error -> {
                    Log.d("Log","Error")
                }

                else -> {}
            }
        }

        viewModel.getUserProfile(token)*/

        // Observe the user profile LiveData

        // Call the function to fetch user profile data
        viewModel.getUserProfile(token)

    }


    private fun updateUI(userProfile: UserProfile) {

        // Load the user avatar using Picasso or your preferred image loading library
        val user = userProfile.user
        loadProfileImage(user.avatar)
        binding.uName.text = user.name
        binding.edtUname.hint = user.email
        binding.recyclerViewTripHistory.adapter = createTripAdapter(user.trips)
//        Log.d("Log",userProfile.email)
    }

    private fun createTripAdapter(trips:List<Trip>): GenericAdapter<Trip> {
        return GenericAdapter<Trip>(
            R.layout.cardview_trip_history, // Replace with your item layout resource
            onBind = { itemView, item ->

                val title: TextView = itemView.findViewById(R.id.txt_trip_title)
                val province: TextView = itemView.findViewById(R.id.txt_trip_province)
                val date: TextView = itemView.findViewById(R.id.txt_trip_date)
                // Load the image using Picasso (you may need to add Picasso as a dependency)
                // Load the image using Picasso (make sure to add Picasso dependency)
                title.text = item.location
                //province.text = item.
                date.text = "From ${item.startDate} to ${item.endDate}"
            },
            onItemClickListener = object : GenericAdapter.OnItemClickListener<Trip> {
                override fun onItemClick(item: Trip) {
                    // Handle the item click here
                    // For example, you can display a Toast message with the item's text
                    //Toast.makeText(requireContext(), "Item clicked: ${item.name}", Toast.LENGTH_SHORT).show()
                    //findNavController().navigate(R.id.action_homeFragment_to_locationFragment)
                }
            }
        ).apply { setData(trips) }
    }

    private fun loadProfileImage(url: String){
        Picasso.get().load(url)
            .into(binding.profileImage)
    }
}