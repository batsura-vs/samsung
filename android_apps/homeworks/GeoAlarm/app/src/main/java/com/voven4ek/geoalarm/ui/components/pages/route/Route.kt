package com.voven4ek.geoalarm.ui.components.pages.route

import androidx.compose.foundation.clickable
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.voven4ek.geoalarm.viewmodel.MainViewModel

@Composable
fun Route(
    model: MainViewModel,
    isPreview: Boolean = false
) {
    val text by model.state.collectAsState()
    Text(
        text = text.toString(),
        modifier = Modifier.clickable {
        }
    )
}

@Preview
@Composable
fun RoutePreview() {
    Surface {
        Route(viewModel())
    }
}