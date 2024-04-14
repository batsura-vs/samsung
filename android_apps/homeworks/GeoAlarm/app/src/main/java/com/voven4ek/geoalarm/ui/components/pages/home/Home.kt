package com.voven4ek.geoalarm.ui.components.pages.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.voven4ek.geoalarm.ui.components.pages.home.components.MapComponent
import com.voven4ek.geoalarm.ui.components.pages.home.components.SearchComponent
import com.voven4ek.geoalarm.ui.components.pages.home.components.SettingsComponent
import com.voven4ek.geoalarm.viewmodel.MainViewModel


@Composable
fun Home(
    model: MainViewModel,
    isPreview: Boolean = false,
) {
    val state = model.state.collectAsState()
    val isSettingsShown = state.value.isSettingsShown

    MapComponent(
        modifier = Modifier.fillMaxSize(), model = model, isPreview = isPreview
    )
    Column {
        SearchComponent(
            model = model,
        )
        AnimatedVisibility(visible = isSettingsShown) {
            SettingsComponent(
                model = model, isPreview = isPreview
            )
        }
    }
}


@Preview
@Composable
fun HomePreview() {
    Home(viewModel(), isPreview = true)
}