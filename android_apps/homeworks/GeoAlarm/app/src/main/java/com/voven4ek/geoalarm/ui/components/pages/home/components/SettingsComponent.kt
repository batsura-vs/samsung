package com.voven4ek.geoalarm.ui.components.pages.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
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
    Column {
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .padding(4.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.background.copy(0.95f))
                .fillMaxWidth(),
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
                Text(text = "Radius: ${radius.roundToInt()}m", fontSize = MaterialTheme.typography.bodyLarge.fontSize)
                Slider(value = radius, onValueChange = {value ->
                    model.updateRadius(value)
                }, valueRange = 30.0f..200.0f)
                AnimatedVisibility(visible = helperActive) {
                    Column {
                        Text(text = "Radius: ${radius.roundToInt()}m", fontSize = MaterialTheme.typography.bodyLarge.fontSize)
                        Slider(value = radius, onValueChange = {value ->
                            model.updateRadius(value)
                        }, valueRange = 30.0f..200.0f)
                    }
                }
            }
        }
    }
}
