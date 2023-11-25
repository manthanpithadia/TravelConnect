import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelconnect.R
import com.example.travelconnect.data.model.CardItemTypeThree
import com.example.travelconnect.data.model.RestaurentItem
import com.example.travelconnect.databinding.FragmentHomeBinding
import com.example.travelconnect.utils.setTransparentStatusBar
import com.example.travelconnect.viewmodels.HomeViewModel
import com.example.travelconnect.views.CardTypeThreeAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.chip.Chip
import com.squareup.picasso.Picasso

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var dbHelper: DBHelper

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val locationPermissionCode = 123
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        setTransparentStatusBar()


        val viewModelFactory = HomeViewModelFactory(requireContext())
        //viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
        dbHelper = DBHelper(requireContext())

        // Check and request location permissions
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                locationPermissionCode
            )
        }

        // Get the current GPS location and make the API request
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val apiKey = "b585612921382105b1098e3cf1652566" // Replace with your API key
                    val latitude = 	location.latitude
                    val longitude = location.longitude
                    getWeatherData(apiKey, latitude, longitude, view)
                } else {
                    // Handle location not available
                    Toast.makeText(requireContext(), "location not available", Toast.LENGTH_SHORT).show()
                }
            }

        val fullText = "Travel Connect"
        val targetText = "Connect"
        createMultiColoredText(binding.title, fullText, targetText)
        binding.btnAdd.setOnClickListener {
            //Toast.makeText(requireContext(), "will implement in Iteration 3", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(view).navigate(R.id.action_homeFragment_to_planTripFragment)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val adapter = ChipAdapter(viewModel.chipItems)
        binding.recyclerView.adapter = adapter

        // card view type 1
        binding.recyclerViewCard1.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        viewModel.getTrendingLocations().observe(viewLifecycleOwner, Observer { locations ->
            // Update the adapter with the data
           // val cardAdapter = CardTypeOneAdapter(requireContext(), locations)
            binding.recyclerViewCard1.adapter = createLocationAdapter(locations)
        })

        // card view type 2
        binding.recyclerViewCard2.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
       // val cardItemTypeTwo = createDummyCardItemsTypeTwo()
        viewModel.getActivities().observe(viewLifecycleOwner, Observer { activities ->
            // Update the adapter with the data
          //  val cardAdapter = CardTypeTwoAdapter(requireContext(), activities)
            binding.recyclerViewCard2.adapter = createActivityAdapter(activities)

        })

        // card view type 3
        binding.recyclerViewCard3.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        viewModel.getRestaurents().observe(viewLifecycleOwner, Observer { restaurants ->
            // Update the adapter with the data
            //  val cardAdapter = CardTypeTwoAdapter(requireContext(), activities)
            binding.recyclerViewCard3.adapter = createRestaurentAdapter(restaurants)
        })

    }

    private fun createActivityAdapter(activities:List<ActivityItem>): GenericAdapter<ActivityItem> {
        return GenericAdapter<ActivityItem>(
            R.layout.cardview_type2, // Replace with your item layout resource
            onBind = { itemView, item ->
                val imageView: ImageView = itemView.findViewById(R.id.img_card_background)
                val titleTextView: TextView = itemView.findViewById(R.id.txt_card_title)
                val locationTextView: TextView = itemView.findViewById(R.id.txt_card_location2)
                // Load the image using Picasso (you may need to add Picasso as a dependency)
                // Load the image using Picasso (make sure to add Picasso dependency)
                Picasso.get().load(item.img).resize(200, 200)
                    .centerCrop().into(imageView)
                titleTextView.text = item.name
                locationTextView.text = item.province
            },
            onItemClickListener = object : GenericAdapter.OnItemClickListener<ActivityItem> {
                override fun onItemClick(item: ActivityItem) {
                    // Handle the item click here
                    // For example, you can display a Toast message with the item's text
                    Toast.makeText(requireContext(), "Item clicked: ${item.name}", Toast.LENGTH_SHORT).show()
                    //findNavController().navigate(R.id.action_homeFragment_to_locationFragment)
                    val args = Bundle().apply {
                        putString("name", item.name)
                    }

                    // TODO: Uncomment this
                    view?.let {
                        Navigation.findNavController(it)
                            .navigate(com.example.travelconnect.R.id.action_homeFragment_to_extendedFragment, args)
                    }
                }
            }
        ).apply { setData(activities) }
    }

    private fun createRestaurentAdapter(activities:List<RestaurentItem>): GenericAdapter<RestaurentItem> {
        return GenericAdapter<RestaurentItem>(
            R.layout.cardview_type3, // Replace with your item layout resource
            onBind = { itemView, item ->
                val imageView: ImageView = itemView.findViewById(R.id.img_card_background3)
                val titleTextView: TextView = itemView.findViewById(R.id.txt_card_title3)
                val locationTextView: TextView = itemView.findViewById(R.id.txt_card_location3)
                val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar3)
                val txtRating: TextView = itemView.findViewById(R.id.txt_rating3)

                Picasso.get().load(item.img).resize(200, 200)
                    .centerCrop().into(imageView)
                titleTextView.text = item.name
                locationTextView.text = item.city
                ratingBar.rating = item.rating.toFloat()
                txtRating.text = item.rating.toFloat().toString()

            },
            onItemClickListener = object : GenericAdapter.OnItemClickListener<RestaurentItem> {
                override fun onItemClick(item: RestaurentItem) {
                    // Handle the item click here
                    // For example, you can display a Toast message with the item's text
                    Toast.makeText(requireContext(), "Item clicked: ${item.name}", Toast.LENGTH_SHORT).show()
                    //findNavController().navigate(R.id.action_homeFragment_to_locationFragment)
                    val args = Bundle().apply {
                        putString("name", item.name)
                    }

                    // TODO: Uncomment this
                    view?.let {
                        Navigation.findNavController(it)
                            .navigate(com.example.travelconnect.R.id.action_homeFragment_to_extendedFragment, args)
                    }
                }
            }
        ).apply { setData(activities) }
    }


    private fun createLocationAdapter(locations:List<LocationItem>): GenericAdapter<LocationItem> {
        return GenericAdapter<LocationItem>(
            R.layout.cardview_type1, // Replace with your item layout resource
            onBind = { itemView, item ->
                val imageView: ImageView = itemView.findViewById(R.id.img_background)
                val titleTextView: TextView = itemView.findViewById(R.id.txt_title)
                val locationTextView: TextView = itemView.findViewById(R.id.txt_location)
                val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)
                val txtRating: TextView = itemView.findViewById(R.id.txt_rating)
                // Load the image using Picasso (you may need to add Picasso as a dependency)
                Picasso.get().load(item.img).resize(200, 200)
                    .centerCrop().into(imageView)

                titleTextView.text = item.name
                locationTextView.text = item.province
                ratingBar.rating = item.rating.toFloat()
                txtRating.text = item.rating.toFloat().toString()

            },
            onItemClickListener = object : GenericAdapter.OnItemClickListener<LocationItem> {
                override fun onItemClick(item: LocationItem) {
                    // Handle the item click here
                    // For example, you can display a Toast message with the item's text
                    Toast.makeText(requireContext(), "Item clicked: ${item.name}", Toast.LENGTH_SHORT).show()

                    // Retrieve the ID based on the location name
                    val selectedId = item.id

                    if (selectedId != null) {
                        // Navigate to LocationFragment and pass name and ID as arguments
                        val args = Bundle().apply {
                            putString("name", item.name)
                            putString("id", selectedId)
                        }

                        view?.let {
                            Navigation.findNavController(it)
                                .navigate(com.example.travelconnect.R.id.action_homeFragment_to_locationFragment, args)
                        }
                    } else {
                        Toast.makeText(requireContext(), "Error retrieving location ID", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        ).apply { setData(locations) }
    }

    private fun createDummyCardItemsTypeThree(): List<CardItemTypeThree> {
        val cardItems = mutableListOf<CardItemTypeThree>()

        // Add dummy horizontal card items
        cardItems.add(
            CardItemTypeThree(
                R.drawable.img_res1,
                "Canvas Café",
                "Alberta",
                3.5f,
                "5.0"
            )
        )
        cardItems.add(
            CardItemTypeThree(
                R.drawable.img_res2,
                "FlavorFusion Bistro",
                "Alberta",
                5.0f,
                "5.0"
            )
        )

        // Add more dummy horizontal card items as needed

        return cardItems
    }
    fun createMultiColoredText(textView: TextView, fullText: String, targetText: String) {
        val spannable = SpannableString(fullText)

        val startIndex = fullText.indexOf(targetText)
        val endIndex = startIndex + targetText.length

        val color = ContextCompat.getColor(requireContext(), R.color.yellow)

        val span = ForegroundColorSpan(color)
        spannable.setSpan(span, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        textView.text = spannable
    }

    private fun getWeatherData(apiKey: String, latitude: Double, longitude: Double, view: View) {
        viewModel.getWeatherData(apiKey, latitude, longitude, { temperature,weather ->
            updateUI(temperature, weather, view)
        }) {
            // Handle error
        }
    }

    private fun updateUI(temperature: Double, city:String, view: View) {
     //   val temperatureTextView = view.findViewById<TextView>(R.id.temperatureTextView)
        val celsiusTemperature =  kelvinToCelsius(temperature)
        val formattedString = String.format("%.2f", celsiusTemperature)
        binding.txtWeather.text = "$formattedString °C in $city"
    }

    fun kelvinToCelsius(kelvin: Double): Double {
        return kelvin - 273.15
    }

}
