import android.app.ActivityManager

import android.content.Context


fun isServiceRunningInForeground(context: Context, serviceClass: Class<*>): Boolean {
    val manager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    for (service in manager.getRunningServices(Int.MAX_VALUE)) {
        if (serviceClass.name == service.service.className) {
            if (service.foreground) {
                return true
            }
        }
    }
    return false
}