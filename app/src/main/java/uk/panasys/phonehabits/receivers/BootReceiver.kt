package uk.panasys.phonehabits.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import uk.panasys.phonehabits.services.CountService

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val countService = Intent(context, CountService::class.java)
        context.startService(countService)
    }
}