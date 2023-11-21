package com.example.travelconnect.utils

import DBHelper
import Restaurant
import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Build
import android.view.View
import androidx.fragment.app.Fragment
import java.util.Calendar

fun Fragment.setTransparentStatusBar() {
    activity?.window?.decorView?.systemUiVisibility =
        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

    // Set the status bar color to transparent (for Android 5.0 and higher)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        activity?.window?.statusBarColor = Color.TRANSPARENT
    }
}

fun Fragment.showDatePickerDialog(listener: DatePickerDialog.OnDateSetListener) {
    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(requireContext(), listener, year, month, day)
    datePickerDialog.show()
}

fun DBHelper.getLocationNamesWithIds(): List<Pair<String, String>> {
    val locations = queryLocations()
    return locations.map { Pair(it.name, it.id) }
}

fun <T : Any> List<T>.createListWithFirstImage(getName: (T) -> String, getImages: (T) -> List<String>): List<Pair<String, String>> {
    return map { item ->
        val name = getName(item)
        val firstImage = getImages(item).firstOrNull() ?: ""
        Pair(name, firstImage)
    }
}


