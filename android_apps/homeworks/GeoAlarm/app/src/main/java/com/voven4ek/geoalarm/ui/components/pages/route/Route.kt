package com.voven4ek.geoalarm.ui.components.pages.route

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.voven4ek.geoalarm.services.LocationService
import com.voven4ek.geoalarm.ui.components.pages.home.components.MapComponent
import com.voven4ek.geoalarm.viewmodel.MainViewModel
import isServiceRunningInForeground
import kotlinx.coroutines.delay

@Composable
fun Route(
    model: MainViewModel, isPreview: Boolean = false
) {
    val context = LocalContext.current
    var isLocationServiceRunning by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        while (true) {
            isLocationServiceRunning =
                isServiceRunningInForeground(context, LocationService::class.java)
            delay(300)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Route", fontSize = 32.sp, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                MapComponent(
                    modifier = Modifier.fillMaxSize(), model = model, isPreview = isPreview
                )
            }

            key(isLocationServiceRunning) {
                Column(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        modifier = Modifier
                            .size(48.dp)
                            .padding(bottom = 4.dp),
                        imageVector = if (isLocationServiceRunning) Icons.Filled.CheckCircle else Icons.Filled.Warning,
                        tint = if (isLocationServiceRunning) Color.Green else Color.Red,
                        contentDescription = "Location Service Status"
                    )
                    Text(
                        text = if (isLocationServiceRunning) "Location Service is running" else "Location Service is stopped",
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }

        key(isLocationServiceRunning) {
            Button(
                onClick = {
                    if (isLocationServiceRunning) {
                        model.stopLocationService(context)
                    } else {
                        model.startLocationService(context)
                    }
                },
                modifier = Modifier
                    .size(width = 180.dp, height = 50.dp)
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(50)
            ) {
                Text(text = if (isLocationServiceRunning) "STOP" else "START")
            }
        }
    }
}