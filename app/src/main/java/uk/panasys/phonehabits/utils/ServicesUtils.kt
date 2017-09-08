package uk.panasys.phonehabits.utils;

import android.app.ActivityManager;
import android.content.Context;

object ServicesUtils {

    fun isServiceRunning(ctx: Context, serviceClass: Class<*>): Boolean {
        val manager: ActivityManager = ctx.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        return manager.getRunningServices(Integer.MAX_VALUE).any { serviceClass.name == it.service.className }
    }
}
