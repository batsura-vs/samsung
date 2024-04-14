package com.voven4ek.geoalarm.ui.components.pages.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.voven4ek.geoalarm.rest.OpenStreetMap
import com.voven4ek.geoalarm.rest.api.OpenStreetMapApi
import com.voven4ek.geoalarm.rest.models.OsmPlace
import com.voven4ek.geoalarm.viewmodel.MainViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint
import kotlin.coroutines.cancellation.CancellationException

enum class SearchStatus {
    NONE, LOADING, ERROR, SUCCESS
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchComponent(
    model: MainViewModel
) {
    var isExpanded by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    var searchStatus by remember { mutableStateOf(SearchStatus.NONE) }
    val searchScope = rememberCoroutineScope()
    val api: OpenStreetMapApi = OpenStreetMap.getApi()
    var places: List<OsmPlace> by remember { mutableStateOf(listOf()) }
    var currentCoroutine by remember { mutableStateOf<Job?>(null) }
    val onSearch: (String) -> Unit = {
        currentCoroutine?.cancel()
        currentCoroutine = searchScope.launch {
            delay(350)
            runCatching {
                searchStatus = SearchStatus.LOADING
                api.searchByQuery(it).let {
                    if (it.isEmpty()) {
                        searchStatus = SearchStatus.NONE
                    } else {
                        searchStatus = SearchStatus.SUCCESS
                        places = it
                    }
                }
            }.onFailure {
                if (it is CancellationException) return@launch
                searchStatus = SearchStatus.ERROR
            }
        }
    }
    SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(SearchBarDefaults.dockedShape),
        query = searchText,
        onQueryChange = ({
            searchText = it
            onSearch(searchText)
        }),
        onSearch = onSearch,
        active = isExpanded,
        onActiveChange = {
            isExpanded = it
            model.updateIsSettingsShown(!it)
            searchStatus = SearchStatus.NONE
        },
        leadingIcon = {
            IconButton(onClick = {
                onSearch(searchText)
            }) {
                Icon(Icons.Filled.Search, contentDescription = "Search button")
            }
        },
        trailingIcon = {
            if (isExpanded) {
                IconButton(
                    onClick = {
                        if (searchText.isNotEmpty()) {
                            searchText = ""
                            searchStatus = SearchStatus.NONE
                        } else {
                            isExpanded = false
                        }
                    },
                ) {
                    Icon(Icons.Filled.Close, contentDescription = "Close button")
                }
            } else {
                IconButton(
                    onClick = {
                        model.updateIsSettingsShown(!model.state.value.isSettingsShown)
                    },
                ) {
                    Icon(Icons.Filled.Settings, contentDescription = "Settings button")
                }
            }
        },
        placeholder = {
            Text(text = "Search")
        },
    ) {
        when (searchStatus) {
            SearchStatus.LOADING -> Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }

            SearchStatus.NONE -> Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Nothing to show", fontSize = 24.sp
                )
            }

            SearchStatus.ERROR -> Box(
                modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Something went wrong", fontSize = 24.sp
                )
            }

            SearchStatus.SUCCESS -> LazyColumn {
                places.forEach { place ->
                    item {
                        PlaceComponent(
                            place = place,
                            onPlaceClick = {
                                model.navigateToGeoPoint(
                                    GeoPoint(
                                        place.lat.toDouble(), place.lon.toDouble()
                                    )
                                )
                                isExpanded = false
                            },
                        )
                    }
                }
            }
        }


    }
}

@Composable
fun PlaceComponent(
    place: OsmPlace,
    onPlaceClick: (OsmPlace) -> Unit = {},
) {
    Card(
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row {
                Icon(
                    Icons.Filled.Home,
                    contentDescription = "Home icon",
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = place.name,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .weight(1f),
                    fontWeight = Bold,
                    fontSize = 20.sp,
                )
                IconButton(
                    onClick = { onPlaceClick(place) },
                ) {
                    Icon(Icons.Filled.LocationOn, contentDescription = "Show on map")
                }
            }
            Text(text = place.displayName)
        }
    }
}