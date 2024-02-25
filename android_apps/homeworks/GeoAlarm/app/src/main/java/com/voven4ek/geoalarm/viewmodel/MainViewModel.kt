package com.voven4ek.geoalarm.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.osmdroid.util.GeoPoint

class MainViewModel : ViewModel() {
    private val _state = MutableStateFlow(AppState())
    val state = _state.asStateFlow()

    fun updateGeoPoint(value: GeoPoint) {
        _state.update { currentState -> currentState.copy(geoPoint = value) }
    }

    fun updateZoom(value: Double) {
        _state.update { currentState -> currentState.copy(zoom = value) }
    }

    fun updateDestination(destination: GeoPoint) {
        _state.update { currentState -> currentState.copy(destination = destination) }
    }
}

data class AppState(
    val geoPoint: GeoPoint = GeoPoint(55.755826, 37.6173),
    val destination: GeoPoint = GeoPoint(55.755826, 37.6173),
    val zoom: Double = 12.0
)