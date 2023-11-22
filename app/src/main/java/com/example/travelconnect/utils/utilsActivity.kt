package com.example.travelconnect.utils

import android.content.Context
import android.util.Log

fun Context.saveToSharedPreferences(token: String?, avatarString: String?) {
    val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()

    editor.putString("token", token)
    editor.putString("avatarString", avatarString)

    editor.apply()
}

fun Context.getFromSharedPreferences() {
    val sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
    val token = sharedPreferences.getString("token", "")
    val avatarString = sharedPreferences.getString("avatarString", "")

    Log.d("Log", "Token: $token, AvatarString: $avatarString")
}



