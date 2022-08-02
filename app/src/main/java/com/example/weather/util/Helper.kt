package com.example.weather.util

import java.text.SimpleDateFormat
import java.util.*

class Helper {

     fun getCurrentDay(): String {
        val calendar = Calendar.getInstance()
        val date = calendar.time
        return SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.time)
    }

     fun greet() : String {
        val c = Calendar.getInstance()
        val timeOfDay = c[Calendar.HOUR_OF_DAY]
        return if (timeOfDay in 0..11) {
            "Good Morning"
        } else if (timeOfDay in 12..15) {
            "Good Afternoon"
        } else if (timeOfDay in 16..20) {
            "Good Evening"
        } else if (timeOfDay in 21..23) {
            "Good Night"
        }else{
            ""
        }
    }
}