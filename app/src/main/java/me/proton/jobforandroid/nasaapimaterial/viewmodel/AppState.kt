package me.proton.jobforandroid.nasaapimaterial.viewmodel

import me.proton.jobforandroid.nasaapimaterial.repository.dto.EarthEpicServerResponseData
import me.proton.jobforandroid.nasaapimaterial.repository.dto.MarsPhotosServerResponseData
import me.proton.jobforandroid.nasaapimaterial.repository.dto.PODServerResponseData
import me.proton.jobforandroid.nasaapimaterial.repository.dto.SolarFlareResponseData

sealed class AppState {
    data class SuccessPOD(val serverResponseData: PODServerResponseData) : AppState()
    data class SuccessEarthEpic(val serverResponseData: List<EarthEpicServerResponseData>) :
        AppState()

    data class SuccessMars(val serverResponseData: MarsPhotosServerResponseData) : AppState()
    data class SuccessWeather(val solarFlareResponseData: List<SolarFlareResponseData>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
