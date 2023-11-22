package com.example.travelconnect.views

import DBHelper
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.travelconnect.R
import com.example.travelconnect.databinding.FragmentPlanTripBinding
import com.example.travelconnect.utils.getFromSharedPreferences
import com.example.travelconnect.utils.getLocationNamesWithIds
import com.example.travelconnect.utils.setTransparentStatusBar
import com.example.travelconnect.utils.showDatePickerDialog
import com.example.travelconnect.viewmodels.PlanTripViewModel
import java.text.SimpleDateFormat
import java.util.Calendar


class PlanTripFragment : Fragment(), DatePickerDialog.OnDateSetListener {
    private lateinit var binding: FragmentPlanTripBinding
    private lateinit var dbHelper: DBHelper
    private lateinit var tripViewModel: PlanTripViewModel
    private var targetTextView: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlanTripBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tripViewModel = ViewModelProvider(this).get(PlanTripViewModel::class.java)

        setTransparentStatusBar()
        setupLocationACTV()
        setupFoodSpinner()


        dbHelper = DBHelper(requireContext())

        // Example: Get location names with IDs
        val locationNamesWithIds = dbHelper.getLocationNamesWithIds()

        // Set up the AutoCompleteTextView adapter
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line,
            locationNamesWithIds.map { it.first })
        binding.actv.setAdapter(adapter)
        binding.actv.threshold = 1

        binding.sDate.setOnClickListener {
            targetTextView = binding.txtSDate
            showDatePickerDialog(this@PlanTripFragment)
        }

        binding.eDate.setOnClickListener {
            targetTextView = binding.txtEDate
            showDatePickerDialog(this@PlanTripFragment)
        }
        val (token, avatarString) = getFromSharedPreferences()
        binding.btnDone.setOnClickListener {
            // Assuming you have the necessary data for the trip
            val location = binding.actv.text.toString()
            val startDate = binding.txtSDate.text.toString()
            val endDate = binding.txtEDate.text.toString()
            val foodPref = binding.dateSpinner.selectedItem.toString()

            tripViewModel.addTrip(token, location, startDate, endDate, foodPref)

            binding.actv.setText("")
            binding.txtSDate.text = ""
            binding.txtEDate.text = ""
            binding.dateSpinner.setSelection(0)
        }

        // Observe the result of adding a trip
        tripViewModel.addTripResultLiveData.observe(viewLifecycleOwner) { result ->
            if (result) {
                Toast.makeText(requireContext(), "trip added", Toast.LENGTH_SHORT).show()
                // Trip added successfully
                // You can navigate to another fragment or perform other actions
                // based on the success of adding the trip
            } else {
                // Failed to add trip
                // Handle the failure scenario
            }
        }


    }


    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val selectedDate = Calendar.getInstance().apply {
            set(year, month, dayOfMonth)
        }

        val formattedDate = SimpleDateFormat("dd/MM/yyyy").format(selectedDate.time)

        // Check if the targetTextView is not null before updating
        targetTextView?.text = formattedDate
    }

    private fun setupLocationACTV() {
        // Create an ArrayAdapter with your data
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, arrayOf("Hi", "Ho"))

        // Set the adapter and threshold for AutoCompleteTextView
        binding.actv.setAdapter(adapter)
        binding.actv.threshold = 1
    }

    private fun setupFoodSpinner() {
        // Create an ArrayAdapter with your data
        val adapter = object : ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, arrayOf("Select Food Pref", "Veg", "Non Veg")) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                val textColor = if (position == 0) {
                    // Set color for the default item ("Select Food Pref") when closed
                    ContextCompat.getColor(requireContext(), R.color.txt_hint)
                } else {
                    // Set color for selected item when closed
                    ContextCompat.getColor(requireContext(), R.color.txt_white)
                }
                (view as TextView).setTextColor(textColor)
                return view
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent)
                val textColor = if (position == 0) {
                    // Set color for the default item ("Select Food Pref") in dropdown
                    ContextCompat.getColor(requireContext(), R.color.txt_hint)
                } else {
                    // Set color for selected item in dropdown
                    ContextCompat.getColor(requireContext(), R.color.txt_white)
                }
                (view as TextView).setTextColor(textColor)
                return view
            }
        }
        // Set the adapter and threshold for AutoCompleteTextView
        binding.dateSpinner.adapter = adapter
    }
}