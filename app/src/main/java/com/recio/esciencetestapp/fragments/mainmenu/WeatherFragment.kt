package com.recio.esciencetestapp.fragments.mainmenu

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import com.android.volley.Request.Method
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.recio.esciencetestapp.R
import com.recio.esciencetestapp.databinding.FragmentWeatherBinding
import com.recio.esciencetestapp.fragments.basefragment.BaseFragment
import org.json.JSONObject

class WeatherFragment : BaseFragment<FragmentWeatherBinding>(FragmentWeatherBinding::inflate) {

    var weatherUrl = ""
    val weatherApiKey = "09bbb85297614be4b65b66d9d5c9d68c"

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        getUserLocation()

    }

    private fun getUserLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            displayDialogBoxMessage(getString(R.string.error), getString(R.string.please_turn_on_your_location), getString(R.string.close))
            return
        } else {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    weatherUrl = "https://api.weatherbit.io/v2.0/current?" + "lat=" + location?.latitude + "&lon=" + location?.longitude + "&key=" + weatherApiKey
                    getTemperature()
                }
        }
    }

    private fun getTemperature() {
        val queue = Volley.newRequestQueue(requireContext())
        val url: String = weatherUrl

        val stringReq = StringRequest(
            Method.GET, url, { response ->
                val jsonObject = JSONObject(response)
                val jsonArray = jsonObject.getJSONArray("data")
                val jsonText = jsonArray.getJSONObject(0)

                binding.textView.text = "It is currently " + jsonText.getString("temp") + " Degree Celsius in " + jsonText.getString("city_name")
            }, { displayToastMessage(getString(R.string.failed_to_retrieve_data))})
        queue.add(stringReq)
    }

}