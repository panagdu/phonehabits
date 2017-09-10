package uk.panasys.phonehabits.activities

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import uk.panasys.phonehabits.R
import uk.panasys.phonehabits.receivers.CounterUpdateReceiver
import uk.panasys.phonehabits.services.CountService
import uk.panasys.phonehabits.utils.ServicesUtils


class MainActivity : AppCompatActivity() {

    companion object {
        val CURRENT_COUNTER = "CURRENT_COUNTER"
    }

    val activityEnhancer = ActivityEnhancer(this)

    private val counterUpdatedReceiver = CounterUpdateReceiver(activityEnhancer)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activityEnhancer.updateCounterAndChart()

        if (!ServicesUtils.isServiceRunning(this, CountService::class.java)) {
            val countService = Intent(this, CountService::class.java)
            startService(countService)
        }

    }

    override fun onResume() {
        super.onResume()

        LocalBroadcastManager.getInstance(this).registerReceiver(
                counterUpdatedReceiver, IntentFilter("counter-updated"))
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(
                counterUpdatedReceiver)
    }
}
