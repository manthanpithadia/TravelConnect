package com.example.travelconnect.views

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.travelconnect.R
import com.example.travelconnect.databinding.FragmentPlanTripBinding
import com.example.travelconnect.utils.setTransparentStatusBar
import com.example.travelconnect.utils.showDatePickerDialog
import java.text.SimpleDateFormat
import java.util.Calendar


class PlanTripFragment : Fragment(), DatePickerDialog.OnDateSetListener {
    private lateinit var binding: FragmentPlanTripBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlanTripBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

         setTransparentStatusBar()
         setupLocationACTV()
         setupFoodSpinner()

        binding.sDate.setOnClickListener {
            showDatePickerDialog(this@PlanTripFragment)
        }
    }


    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val selectedDate = Calendar.getInstance().apply {
            set(year, month, dayOfMonth)
        }

        val formattedDate = SimpleDateFormat("dd/MM/yyyy").format(selectedDate.time)

        binding.txtSDate.text = "$formattedDate"
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