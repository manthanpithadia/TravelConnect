package com.example.travelconnect.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.travelconnect.databinding.FragmentSearchBinding
import com.example.travelconnect.databinding.FragmentUserProfileBinding
import com.example.travelconnect.utils.setTransparentStatusBar
import com.example.travelconnect.viewmodels.SearchViewModel

class UserProfileFragment : Fragment() {

    private lateinit var binding: FragmentUserProfileBinding
   // private lateinit var viewModel: SearchViewModel

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

    }
}