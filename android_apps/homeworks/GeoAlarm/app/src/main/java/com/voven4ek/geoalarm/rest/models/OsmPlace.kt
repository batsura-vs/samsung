package com.voven4ek.geoalarm.rest.models


import com.google.gson.annotations.SerializedName

data class OsmPlace(
    @SerializedName("addresstype") val addresstype: String,
    @SerializedName("boundingbox") val boundingbox: List<String>,
    @SerializedName("class") val classX: String,
    @SerializedName("display_name") val displayName: String,
    @SerializedName("importance") val importance: Double,
    @SerializedName("lat") val lat: String,
    @SerializedName("licence") val licence: String,
    @SerializedName("lon") val lon: String,
    @SerializedName("name") val name: String,
    @SerializedName("osm_id") val osmId: Long,
    @SerializedName("osm_type") val osmType: String,
    @SerializedName("place_id") val placeId: Long,
    @SerializedName("place_rank") val placeRank: Long,
    @SerializedName("type") val type: String
)