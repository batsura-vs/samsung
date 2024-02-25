package com.voven4ek.geoalarm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.voven4ek.geoalarm.ui.components.App
import com.voven4ek.geoalarm.ui.theme.GeoAlarmTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GeoAlarmTheme {
                App()
            }
        }
    }
}