package com.voven4ek.geoalarm.ui.components.pages.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.voven4ek.geoalarm.viewmodel.MainViewModel
import kotlin.math.roundToInt

@Composable
fun SettingsComponent(
    model: MainViewModel,
    isPreview: Boolean = false
) {
    val appState = model.state.collectAsState()
    var helperActive by remember { mutableStateOf(false) }
    val radius: Float = appState.value.radius;
    Card(
        modifier = Modifier
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Settings",
                fontSize = MaterialTheme.typography.titleLarge.fontSize
            )
            HorizontalDivider(
                modifier = Modifier
                    .padding(16.dp)
            )
            Column {
                Text(
                    text = "Radius: ${radius.roundToInt()}m",
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize
                )
                Slider(value = radius, onValueChange = { value ->
                    model.updateRadius(value)
                }, valueRange = 30.0f..200.0f)
            }
        }
    }
}
