package com.voven4ek.geoalarm.ui.components.pages.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.voven4ek.geoalarm.viewmodel.MainViewModel
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.events.MapListener
import org.osmdroid.events.ScrollEvent
import org.osmdroid.events.ZoomEvent
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polygon

var invalidate: () -> Unit = {}

@Composable
fun MapComponent(
    modifier: Modifier,
    model: MainViewModel = viewModel(),
    isPreview: Boolean = false,
) {
    val appState = model.state.collectAsState()
    val geoPoint = appState.value.geoPoint
    val zoom = appState.value.zoom
    val destination = appState.value.destination
    if (isPreview) {
        Box(
            modifier = modifier.background(Color.Green),
        )
    } else {
        val radius = appState.value.radius;
        key(radius) {
            AndroidView(
                modifier = modifier,
                factory = { context ->
                    MapView(context).apply {
                        val marker = Marker(this)
                        val oPolygon = Polygon(this)
                        val updateOPolygonPoints = {
                            val circlePoints: MutableList<GeoPoint> = mutableListOf()
                            for (i in 0..360) {
                                circlePoints.add(
                                    GeoPoint(
                                        marker.position.latitude, marker.position.longitude
                                    ).destinationPoint(
                                        radius.toDouble(), i.toDouble()
                                    )
                                )
                            }
                            oPolygon.title = "Radius: ${radius}m"
                            oPolygon.points = circlePoints
                            invalidate()
                        }
                        marker.title = "Destination"
                        marker.position = destination
                        setTileSource(TileSourceFactory.MAPNIK)
                        addMapListener(object : MapListener {
                            override fun onScroll(event: ScrollEvent?): Boolean {
                                if (event == null) return false
                                model.updateGeoPoint(mapCenter as GeoPoint)
                                return true
                            }

                            override fun onZoom(event: ZoomEvent?): Boolean {
                                if (event == null) return false
                                model.updateZoom(event.zoomLevel)
                                return true
                            }
                        })
                        val events = MapEventsOverlay(object : MapEventsReceiver {
                            override fun singleTapConfirmedHelper(p: GeoPoint?): Boolean {
                                marker.position = p
                                updateOPolygonPoints()
                                if (p != null) {
                                    model.updateDestination(p)
                                }
                                invalidate()
                                return true
                            }

                            override fun longPressHelper(p: GeoPoint?): Boolean {
                                return true
                            }
                        })
                        oPolygon.title = null
                        updateOPolygonPoints()
                        setBuiltInZoomControls(false)
                        overlays.add(events)
                        overlays.add(oPolygon)
                        overlays.add(marker)
                        Configuration.getInstance().userAgentValue = "GeoAlarm"
                        setMultiTouchControls(true)
                        controller.setZoom(zoom)
                        controller.setCenter(geoPoint)
                        invalidate = { invalidate() }
                    }
                },
            )
        }
    }
}