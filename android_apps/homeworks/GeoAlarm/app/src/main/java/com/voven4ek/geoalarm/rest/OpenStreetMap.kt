package com.voven4ek.geoalarm.rest

import com.voven4ek.geoalarm.rest.api.OpenStreetMapApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object OpenStreetMap {
    private const val BASE_URL = "https://nominatim.openstreetmap.org/"

    private fun getDefault(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getApi(): OpenStreetMapApi {
        return getDefault().create(OpenStreetMapApi::class.java)
    }
}