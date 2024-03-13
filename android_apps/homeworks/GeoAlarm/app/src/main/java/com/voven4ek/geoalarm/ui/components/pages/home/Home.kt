package com.voven4ek.geoalarm.ui.components.pages.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.voven4ek.geoalarm.ui.components.pages.home.components.MapComponent
import com.voven4ek.geoalarm.ui.components.pages.home.components.SettingsComponent
import com.voven4ek.geoalarm.viewmodel.MainViewModel


@Composable
fun Home(
    model: MainViewModel,
    isPreview: Boolean = false,
) {
    MapComponent(
        modifier = Modifier.fillMaxSize(),
        model = model,
        isPreview = isPreview
    )
    SettingsComponent(
        model = model,
        isPreview = isPreview
    )
}


@Preview
@Composable
fun HomePreview() {
    Surface {
        Home(viewModel(), isPreview = true)
    }
}