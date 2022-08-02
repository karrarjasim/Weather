package com.example.weather.ui

import WeatherResponse
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.weather.R
import com.example.weather.databinding.ActivityMainBinding
import com.example.weather.util.Helper
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    lateinit var helper: Helper
    val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        helper = Helper()
        setContentView(binding.root)
        getCurrentWeather()
    }

    private fun getCurrentWeather(){
        val request = Request.Builder()
            .url("https://api.tomorrow.io/v4/timelines?location=31.06067884449576%2C%2046.242266959199384&fields=temperatureApparent&fields=temperature&fields=humidity&fields=windSpeed&units=metric&timesteps=current&startTime=now&endTime=nowPlus1h&timezone=Asia%2FBaghdad&apikey=xn9Qfa1VZf3cUi4iw6ltm0P87aRLft0Y")
            .get()
            .addHeader("Accept", "application/json")
            .build()

        val response = client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.v(TAG, e.toString())
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let { jsonString ->
                    val result = Gson().fromJson(jsonString, WeatherResponse::class.java)
                    Log.v(TAG, result.toString())
                    runOnUiThread {
                        binding.apply {
                            greeting.text = helper.greet()
                            day.text = helper.getCurrentDay()
                            progressBar.isVisible = false
                            imageView.isVisible = true
                            val values = result.data.timelines.first().intervals.first().values
                            temperature.text = values.temperature.toString()
                            feels.text = values.temperatureApparent.toString()
                            humidity.text = values.humidity.toString()
                            wind.text = values.windSpeed.toString()
                            if (helper.greet() != "Good Night"){
                                imageView.setImageResource(R.drawable.day)
                            }else{
                                imageView.setImageResource(R.drawable.night)
                            }

                        }
                    }
                }
            }

        })

    }



    companion object {
        const val TAG = "MainActivity"
    }
}