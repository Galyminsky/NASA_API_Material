package me.proton.jobforandroid.nasaapimaterial.repository.dto

import com.google.gson.annotations.SerializedName

data class MarsPhotosServerResponseData(
    @field:SerializedName("photos") val photos: ArrayList<MarsServerResponseData>,
)