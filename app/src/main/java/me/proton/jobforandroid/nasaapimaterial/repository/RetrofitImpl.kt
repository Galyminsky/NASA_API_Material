package me.proton.jobforandroid.nasaapimaterial.repository

import com.google.gson.GsonBuilder
import me.proton.jobforandroid.nasaapimaterial.repository.dto.EarthEpicServerResponseData
import me.proton.jobforandroid.nasaapimaterial.repository.dto.MarsPhotosServerResponseData
import me.proton.jobforandroid.nasaapimaterial.repository.dto.PODServerResponseData
import me.proton.jobforandroid.nasaapimaterial.repository.dto.SolarFlareResponseData
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitImpl {

    companion object {
        private const val BASE_URL = "https://api.nasa.gov/"
    }

    private val api by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
            .create(RetrofitApi::class.java)
    }

    fun getPictureOfTheDay(
        apiKey: String,
        date: String,
        podCallback: Callback<PODServerResponseData>
    ) {
        api.getPictureOfTheDay(apiKey, date).enqueue(podCallback)
    }

    // Earth Polychromatic Imaging Camera
    fun getEPIC(apiKey: String, epicCallback: Callback<List<EarthEpicServerResponseData>>) {
        api.getEPIC(apiKey).enqueue(epicCallback)
    }

    fun getMarsPictureByDate(
        earth_date: String,
        apiKey: String,
        marsCallbackByDate: Callback<MarsPhotosServerResponseData>
    ) {
        api.getMarsImageByDate(earth_date, apiKey).enqueue(marsCallbackByDate)
    }

    fun getSolarFlare(
        apiKey: String,
        podCallback: Callback<List<SolarFlareResponseData>>,
        startDate: String = "2021-09-07"
    ) {
        api.getSolarFlare(apiKey, startDate).enqueue(podCallback)
    }
}