package com.voven4ek.geoalarm.ui.components

import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
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
fun AppNavigation(
    navController: NavHostController, model: MainViewModel, isPreview: Boolean = false
) {
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
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            Toast.makeText(context, "Permission granted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }
    val requestPermissions = { permission: String, callback: () -> Unit ->
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                context, permission
            ) -> {
                callback()
            }

            else -> {
                launcher.launch(permission)
            }
        }
    }
    Scaffold(bottomBar = {
        BottomAppBar(actions = {
            IconButton(onClick = { navController.navigate("home") }) {
                Icon(
                    Icons.Outlined.Home, contentDescription = "Home"
                )
            }
        }, floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    requestPermissions(android.Manifest.permission.ACCESS_FINE_LOCATION) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            requestPermissions(
                                android.Manifest.permission.POST_NOTIFICATIONS
                            ) {
                                navController.navigate("route")
                            }
                        } else {
                            navController.navigate("route")
                        }
                    }
                }, elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(
                    Icons.Filled.PlayArrow, contentDescription = "Start route"
                )
            }
        })
    }) { innerPadding ->
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