package com.voven4ek.geoalarm.ui.components.pages.route

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.voven4ek.geoalarm.services.LocationService
import com.voven4ek.geoalarm.ui.components.pages.home.components.MapComponent
import com.voven4ek.geoalarm.viewmodel.MainViewModel

@Composable
fun Route(
    model: MainViewModel,
    isPreview: Boolean = false
) {
    val state by model.state.collectAsState()
    val context = LocalContext.current
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        MapComponent(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(8.dp)),
            model = model,
            isPreview = isPreview
        )
        Button(onClick = {
            LocationService.startService(context, "Starting service");
        }) {

        }
    }
}

@Preview
@Composable
fun RoutePreview() {
    Surface {
        Route(viewModel())
    }
}