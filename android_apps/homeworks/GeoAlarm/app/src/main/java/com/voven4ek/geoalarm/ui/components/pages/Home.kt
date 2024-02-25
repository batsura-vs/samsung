package com.voven4ek.geoalarm.ui.components.pages

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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


@Composable
fun Home(
    model: MainViewModel
) {
    val appState = model.state.collectAsState()
    val geoPoint = appState.value.geoPoint
    val zoom = appState.value.zoom
    val destination = appState.value.destination
    var mapActive by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        AndroidView(modifier = Modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(16.dp))
            .animateContentSize()
            .height(if (mapActive) 400.dp else 200.dp), factory = { context ->
            MapView(context).apply {
                val marker = Marker(this)
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

                overlays.add(events)
                overlays.add(marker)
                Configuration.getInstance().userAgentValue = "GeoAlarm"
                setMultiTouchControls(true)
                controller.setZoom(zoom)
                controller.setCenter(geoPoint)
            }
        })
        Text(text = "HELLO", modifier = Modifier.clickable {
            mapActive = !mapActive
        })
    }
}

@Preview
@Composable
fun HomePreview() {
    Surface {
        Home(viewModel())
    }
}