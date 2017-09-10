package uk.panasys.phonehabits.receivers;

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import uk.panasys.phonehabits.activities.ActivityEnhancer

class CounterUpdateReceiver(private val activityEnhancer: ActivityEnhancer) : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        activityEnhancer.updateCounterAndChart()
    }
}
