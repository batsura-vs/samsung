package com.voven4ek.geoalarm.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.voven4ek.geoalarm.services.LocationService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.osmdroid.api.IMapController
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polygon

class MainViewModel : ViewModel() {
    private val _state = MutableStateFlow(AppState())
    val state = _state.asStateFlow()
    var mapState = MapState()

    fun updateIsSettingsShown(value: Boolean) {
        _state.update { currentState -> currentState.copy(isSettingsShown = value) }
    }

    fun updateGeoPoint(value: GeoPoint) {
        _state.update { currentState -> currentState.copy(geoPoint = value) }
    }

    fun updateZoom(value: Double) {
        _state.update { currentState -> currentState.copy(zoom = value) }
    }

    fun updateDestination(destination: GeoPoint) {
        _state.update { currentState -> currentState.copy(destination = destination) }
        redrawRadius()
    }

    fun updateRadius(value: Float) {
        _state.update { currentState -> currentState.copy(radius = value) }
        redrawRadius()
    }

    fun startLocationService(context: Context) {
        LocationService.startService(context, state.value.destination, state.value.radius)
    }

    fun stopLocationService(context: Context) {
        LocationService.stopService(context)
    }

    fun navigateToGeoPoint(geoPoint: GeoPoint) {
        updateDestination(geoPoint)
        updateGeoPoint(geoPoint)
        mapState.mapController?.animateTo(geoPoint)
        mapState.marker?.setPosition(geoPoint)
        redrawRadius()
    }

    private fun redrawRadius() {
        mapState.marker?.let {
            val circlePoints: MutableList<GeoPoint> = mutableListOf()
            for (i in 0..360) {
                circlePoints.add(
                    GeoPoint(
                        it.position.latitude, it.position.longitude
                    ).destinationPoint(
                        state.value.radius.toDouble(), i.toDouble()
                    )
                )
            }
            mapState.radiusPolygon?.title = "Radius: ${state.value.radius}m"
            mapState.radiusPolygon?.points = circlePoints
            mapState.invalidate()
        }
    }
}


data class MapState(
    val mapController: IMapController? = null,
    val marker: Marker? = null,
    val radiusPolygon: Polygon? = null,
    val invalidate: () -> Unit = {
        Log.d("MapState", "invalidate")
    },
)

data class AppState(
    val geoPoint: GeoPoint = GeoPoint(55.755826, 37.6173),
    val destination: GeoPoint = GeoPoint(55.755826, 37.6173),
    val zoom: Double = 12.0,
    val radius: Float = 50f,
    val isSettingsShown: Boolean = false
)