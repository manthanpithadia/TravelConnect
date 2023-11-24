package com.example.travelconnect.views

import GenericAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelconnect.R
import com.example.travelconnect.data.model.ExtendedViewState
import com.example.travelconnect.data.model.Reviews
import com.example.travelconnect.data.remote.LocationApiClient
import com.example.travelconnect.data.repositories.Repository
import com.example.travelconnect.databinding.FragmentExtendedBinding
import com.example.travelconnect.utils.setTransparentStatusBar
import com.example.travelconnect.viewmodels.ExtendedViewModel
import com.example.travelconnect.viewmodels.ExtendedViewModelFactory
import com.squareup.picasso.Picasso

class ExtendedFragment : Fragment()  {

    private lateinit var binding: FragmentExtendedBinding
    private lateinit var viewModel: ExtendedViewModel
    private lateinit var imageAdapter: GenericAdapter<String>
    private lateinit var reviewAdapter: GenericAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExtendedBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTransparentStatusBar()
        val name = arguments?.getString("name")

        viewModel = ViewModelProvider(
            this,
            ExtendedViewModelFactory(Repository(LocationApiClient.apiService)))[ExtendedViewModel::class.java]
        imageAdapter = createImageAdapter()

        binding.btnExtendedDirection.setOnClickListener {
            openWebViewFragment("https://www.google.com/maps/dir/waterloo/$name")
        }

        // Set up to display card_view1 data
        binding.recyclerViewExtendedImgs.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewExtendedImgs.adapter = imageAdapter
        binding.recyclerViewReviews.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        viewModel.extendedDetailsLiveData.observe(viewLifecycleOwner) { state ->
            when (state) {
                is ExtendedViewState.Loading -> {
                    Log.d("Log","loading")
                }

                is ExtendedViewState.Success -> {
                    val extendedDetails = state.locationDetails
                    Log.d("Log",extendedDetails.toString())
                    Log.d("Log",extendedDetails.img.toString())
                    Log.d("Log",state.locationDetails.reviews.toString())
                    Picasso.get().load(extendedDetails.img[0]).fit().centerCrop().into(binding.bgExtendedTitleImg)
                    val imageUrls = extendedDetails.img.subList(1, extendedDetails.img.size)
                    binding.txtExtendedDesc.text = extendedDetails.desc
                    imageAdapter.setData(imageUrls)
                    binding.recyclerViewReviews.adapter = createReviewAdapter(extendedDetails.reviews)
                }
                is ExtendedViewState.Error -> {
                    val errorMessage = state.errorMessage
                    // Handle error, show error message, etc.
                    Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
                    Log.d("Log","loading")
                }
                else -> {}
            }
        }
        name?.let { viewModel.getExtendedDetails(it) }
    }

    private fun createImageAdapter(): GenericAdapter<String> {
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

    private fun createReviewAdapter(activities:List<Reviews>): GenericAdapter<Reviews> {
        return GenericAdapter<Reviews>(
            R.layout.cardview_extended_review, // Replace with your item layout resource
            onBind = { itemView, item ->
                val uname: TextView = itemView.findViewById(R.id.txt_uname)
                val desc: TextView = itemView.findViewById(R.id.txt_desc)

                uname.text = item.name
                desc.text = item.description
            },
            onItemClickListener = object : GenericAdapter.OnItemClickListener<Reviews> {
                override fun onItemClick(item: Reviews) {
                    // Handle the item click here
                    // For example, you can display a Toast message with the item's text
                    Toast.makeText(requireContext(), "Item clicked: ${item.name}", Toast.LENGTH_SHORT).show()
                    //findNavController().navigate(R.id.action_homeFragment_to_locationFragment)
                }
            }
        ).apply { setData(activities) }
    }

    private fun openWebViewFragment(url: String) {
        val args = Bundle().apply {
            putString("url", url)
        }

        view?.let {
            Navigation.findNavController(it)
                .navigate(com.example.travelconnect.R.id.action_extendedFragment_to_webViewFragment, args)
        }
    }

}