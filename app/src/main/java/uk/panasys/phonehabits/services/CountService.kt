package uk.panasys.phonehabits.services

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import uk.panasys.phonehabits.receivers.ScreenOnReceiver

class CountService : Service() {

    private var screenOnReceiver: BroadcastReceiver? = null

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        val intentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_SCREEN_ON)
        screenOnReceiver = ScreenOnReceiver()
        registerReceiver(screenOnReceiver, intentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(screenOnReceiver)
    }

}