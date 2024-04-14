package com.voven4ek.geoalarm.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.voven4ek.geoalarm.R
import org.osmdroid.util.GeoPoint




class LocationService : Service() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var destination: GeoPoint
    private var alarmThreshold = 50.0f
    private var isAlarmTriggered = false
    private var ringtone: Ringtone? = null

    override fun onCreate() {
        super.onCreate()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        createLocationRequest()
        createLocationCallback()

        ringtone = RingtoneManager.getRingtone(
            applicationContext, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ringtone?.isLooping = true
        }
    }

    private fun stopAlarmSound() {
        ringtone?.stop()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        if (intent?.action == "STOP_SERVICE") {
            stopSelf()
            stopAlarmSound()
        } else {
            val lat: Double = intent?.getDoubleExtra("lat", .0) ?: .0
            val lon: Double = intent?.getDoubleExtra("lon", .0) ?: .0
            alarmThreshold = intent?.getFloatExtra("radius", 0f) ?: 0f
            destination = GeoPoint(lat, lon)
            startForegroundService()
            requestLocationUpdates()
        }
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY, 1000
        ).build()
    }

    private fun createLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                val location = locationResult.lastLocation ?: return

                if (!location.hasAccuracy()) return

                val distance = calculateDistance(location, destination)
                if (distance <= alarmThreshold) {
                    triggerAlarm()
                }
            }
        }
    }

    private fun requestLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            Log.e("LocationService", "Missing location permissions")
            return
        }
        fusedLocationClient.requestLocationUpdates(
            locationRequest, locationCallback, Looper.getMainLooper()
        )
    }

    private fun calculateDistance(currentLocation: Location, destination: GeoPoint): Float {
        val results = FloatArray(1)
        Location.distanceBetween(
            currentLocation.latitude,
            currentLocation.longitude,
            destination.latitude,
            destination.longitude,
            results
        )
        return results[0]
    }

    private fun triggerAlarm() {
        if (isAlarmTriggered) return
        Log.d("LocationService", "Alarm triggered!")
        isAlarmTriggered = true

        val stopServiceIntent = Intent(this, LocationService::class.java)
        stopServiceIntent.action = "STOP_SERVICE"
        val stopPendingIntent = PendingIntent.getService(
            this,
            0,
            stopServiceIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification =
            NotificationCompat.Builder(this, NOTIFICATION_CHANNEL).setContentTitle("GeoAlarm")
                .setContentText("You have reached your destination!")
                .setSmallIcon(R.drawable.ic_launcher_foreground).addAction(
                    R.drawable.ic_launcher_foreground, "Stop", stopPendingIntent
                ).setAutoCancel(true).build()

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, notification)

        ringtone?.play()
    }

    private fun startForegroundService() {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL, "Location", NotificationManager.IMPORTANCE_DEFAULT
        )
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.createNotificationChannel(channel)

        val notificationIntent = Intent(this, Class.forName(MAIN_ACTIVITY_CLASS_NAME))
        val pendingIntent = PendingIntent.getActivity(
            this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE
        )

        val notification =
            NotificationCompat.Builder(this, NOTIFICATION_CHANNEL).setContentTitle("GeoAlarm")
                .setContentText("Tracking your location...")
                .setSmallIcon(R.drawable.ic_launcher_foreground).setContentIntent(pendingIntent)
                .build()

        startForeground(NOTIFICATION_ID, notification)
    }


    override fun onDestroy() {
        super.onDestroy()
        fusedLocationClient.removeLocationUpdates(locationCallback)
        stopService(this)
        stopAlarmSound()
    }


    companion object {
        private const val NOTIFICATION_ID = 1
        private const val NOTIFICATION_CHANNEL = "location"
        private const val MAIN_ACTIVITY_CLASS_NAME = "com.voven4ek.geoalarm.MainActivity"

        fun startService(context: Context, destination: GeoPoint, radius: Float) {
            val startIntent = Intent(context, LocationService::class.java)
            startIntent.action = "START_SERVICE"
            startIntent.putExtra("lat", destination.latitude)
            startIntent.putExtra("lon", destination.longitude)
            startIntent.putExtra("radius", radius)
            ContextCompat.startForegroundService(context, startIntent)
        }

        fun stopService(context: Context) {
            val stopIntent = Intent(context, LocationService::class.java)
            context.stopService(stopIntent)
        }
    }

}