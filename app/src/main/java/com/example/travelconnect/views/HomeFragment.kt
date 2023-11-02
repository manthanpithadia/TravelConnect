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
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.travelconnect.R
import com.example.travelconnect.data.model.CardItemTypeThree
import com.example.travelconnect.databinding.FragmentHomeBinding
import com.example.travelconnect.viewmodels.HomeViewModel
import com.example.travelconnect.views.CardTypeThreeAdapter
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel

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
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

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
        binding.btnAdd.setOnClickListener { Toast.makeText(requireContext(), "will implement in Iteration 3", Toast.LENGTH_SHORT).show() }
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val adapter = ChipAdapter(viewModel.chipItems)
        binding.recyclerView.adapter = adapter

        // card view type 1
        binding.recyclerViewCard1.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        viewModel.getTrendingLocations().observe(viewLifecycleOwner, Observer { locations ->
            // Update the adapter with the data
            val cardAdapter = CardTypeOneAdapter(requireContext(), locations)
            binding.recyclerViewCard1.adapter = cardAdapter
        })

        // card view type 2
        binding.recyclerViewCard2.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
       // val cardItemTypeTwo = createDummyCardItemsTypeTwo()
        viewModel.getActivities().observe(viewLifecycleOwner, Observer { locations ->
            // Update the adapter with the data
            val cardAdapter = CardTypeTwoAdapter(requireContext(), locations)
            binding.recyclerViewCard2.adapter = cardAdapter
        })

        // card view type 2
        binding.recyclerViewCard3.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val cardItemTypeThree = createDummyCardItemsTypeThree()
        binding.recyclerViewCard3.adapter = CardTypeThreeAdapter(requireContext(), cardItemTypeThree)
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
