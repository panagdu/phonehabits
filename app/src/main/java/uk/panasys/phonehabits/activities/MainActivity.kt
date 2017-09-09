package uk.panasys.phonehabits.activities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import uk.panasys.phonehabits.R
import uk.panasys.phonehabits.chart.IntValueFormatter
import uk.panasys.phonehabits.services.CountService
import uk.panasys.phonehabits.utils.ServicesUtils
import uk.panasys.phonehabits.utils.SharedPrefUtils


class MainActivity : AppCompatActivity() {

    companion object {
        val CURRENT_COUNTER = "CURRENT_COUNTER"
    }

    val activityEnhancer = ActivityEnhancer(this)

    private val counterUpdatedReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            activityEnhancer.updateCounterAndChart()
        }
    }

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

    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(
                counterUpdatedReceiver)
    }
}
