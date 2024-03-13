package com.voven4ek.geoalarm.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.voven4ek.geoalarm.ui.components.pages.home.Home
import com.voven4ek.geoalarm.ui.components.pages.route.Route
import com.voven4ek.geoalarm.ui.theme.GeoAlarmTheme
import com.voven4ek.geoalarm.viewmodel.MainViewModel

@Composable
fun AppNavigation(navController: NavHostController, model: MainViewModel, isPreview: Boolean = false) {
    NavHost(navController = navController, "home") {
        composable("home") {
            Home(model = model, isPreview = isPreview)
        }
        composable("route") {
            Route(model = model, isPreview = isPreview)
        }
    }
}

@Composable
fun App(
    model: MainViewModel = viewModel(),
    isPreview: Boolean = false,
) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(onClick = { navController.navigate("home") }) {
                        Icon(
                            Icons.Outlined.Home,
                            contentDescription = "Home"
                        )
                    }
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { navController.navigate("route") },
                        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
                    ) {
                        Icon(
                            Icons.Filled.PlayArrow,
                            contentDescription = "Start route"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier.padding(innerPadding)
        ) {
            AppNavigation(navController, model, isPreview = isPreview)
        }
    }
}

@Preview
@Composable
fun AppPreview() {
    GeoAlarmTheme {
        App(isPreview = true)
    }
}