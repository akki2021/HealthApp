package com.example.healthapp.data

import android.content.Context
import android.content.SharedPreferences
import com.example.healthapp.activity.Login

class Prefs(context: Context) {

    private val preferences: SharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE)

    var status : Int
        get() = preferences.getInt("status", -1)
        set(value) = preferences.edit().putInt("status", value).apply()

    var username: String?
        get() = preferences.getString("username", "anam1")
        set(value) = preferences.edit().putString("username", value).apply()

    var name: String?
        get() = preferences.getString("name", "anam")
        set(value) = preferences.edit().putString("name", value).apply()

    var email: String?
        get()= preferences.getString("email", "anamansari062@gmail.com")
        set(value) = preferences.edit().putString("email", value).apply()

    var age : Int
        get() = preferences.getInt("status", -1)
        set(value) = preferences.edit().putInt("age", value).apply()

}