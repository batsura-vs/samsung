package com.voven4ek.geoalarm.ui.components.pages

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
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
    model: MainViewModel,
    isPreview: Boolean = false,
) {
    var helperActive by remember { mutableStateOf(false) }
    val helperSize by animateFloatAsState(
        targetValue = if (helperActive) 2f else 0.4f, label = "helper size"
    )
    MapComponent(
        modifier = Modifier.fillMaxSize(), model = model, isPreview = isPreview
    )
    Column {
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .padding(4.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.background.copy(0.95f))
                .fillMaxWidth()
                .weight(helperSize),
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    Text(
                        text = "Settings",
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        modifier = Modifier.align(Alignment.CenterVertically),
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(
                        onClick = {
                            helperActive = !helperActive
                        },
                        modifier = Modifier.align(Alignment.CenterVertically),
                    ) {
                        Icon(Icons.Filled.Menu, contentDescription = "Settings")
                    }
                }
                LazyColumn(
                    modifier = Modifier
                        .weight(1f),
                    content = {
                        items(listOf("1", "2", "3")) {
                            Card(
                                modifier = Modifier.padding(4.dp)
                            ) {
                                ListItem(
                                    headlineContent = {
                                        Text(text = "Item $it")
                                    },
                                )
                            }
                        }
                    },
                )
                Row {
                    Spacer(modifier = Modifier.weight(1f))
                    Button(
                        onClick = { /*TODO*/ },
                        colors = ButtonDefaults.elevatedButtonColors(),
                    ) {
                        Text(text = "Save")
                    }
                }
            }
        }
    }
}

@Composable
fun MapComponent(
    modifier: Modifier, model: MainViewModel = viewModel(), isPreview: Boolean = false
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
        AndroidView(modifier = modifier, factory = { context ->
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
                setBuiltInZoomControls(false)
                overlays.add(events)
                overlays.add(marker)
                Configuration.getInstance().userAgentValue = "GeoAlarm"
                setMultiTouchControls(true)
                controller.setZoom(zoom)
                controller.setCenter(geoPoint)
            }
        })
    }
}

@Preview
@Composable
fun HomePreview() {
    Surface {
        Home(viewModel(), isPreview = true)
    }
}