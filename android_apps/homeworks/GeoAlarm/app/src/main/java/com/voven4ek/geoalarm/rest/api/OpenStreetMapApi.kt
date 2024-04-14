package com.voven4ek.geoalarm.rest.api

import com.voven4ek.geoalarm.rest.models.OsmPlace
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface OpenStreetMapApi {
    @GET("/search?format=json")
    suspend fun searchByQuery(
        @Query("q") query: String,
        @Header("accept-language") language: String = "ru"
    ): List<OsmPlace>
}