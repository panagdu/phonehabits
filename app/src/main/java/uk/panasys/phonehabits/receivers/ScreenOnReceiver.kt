package uk.panasys.phonehabits.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.v4.content.LocalBroadcastManager
import uk.panasys.phonehabits.R
import uk.panasys.phonehabits.activities.MainActivity
import uk.panasys.phonehabits.utils.SharedPrefUtils
import java.text.DateFormat
import java.util.*

class ScreenOnReceiver : BroadcastReceiver() {
    private val LATEST_SEVEN_DAYS = "LATEST_SEVEN_DAYS"
    private val LATEST_KNOWN_DATE = "LATEST_KNOWN_DATE"
    private val VALUES_PER_HOUR = "VALUES_PER_HOUR"
    private val CURRENT_TOTAL_COUNTER = "CURRENT_TOTAL_COUNTER"


    override fun onReceive(context: Context, intent: Intent) {
        increaseTotal(context)
        increaseOrResetCounter(context)
        calculateValuesPerDay(context)
    }

    private fun increaseTotal(context: Context) {
        val currentTotalCounter = SharedPrefUtils.getPreference(context, CURRENT_TOTAL_COUNTER, 0)
        SharedPrefUtils.setPreference(context, CURRENT_TOTAL_COUNTER, currentTotalCounter + 1)
    }

    private fun calculateValuesPerDay(context: Context) {
        val hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        val valuesPerHourPreference = SharedPrefUtils.getPreference(context, VALUES_PER_HOUR, context.getString(R.string.average_per_hour))
        val valuesPerHour = valuesPerHourPreference.split(", ").toMutableList()
        valuesPerHour[hour] = valuesPerHour[hour].toInt().plus(1).toString()
        SharedPrefUtils.setPreference(context, VALUES_PER_HOUR, valuesPerHour.joinToString())
    }


    private fun increaseOrResetCounter(context: Context) {
        val currentCounter = SharedPrefUtils.getPreference(context, MainActivity.CURRENT_COUNTER, 0)
        val lastKnownDate = SharedPrefUtils.getPreference(context, LATEST_KNOWN_DATE, "")
        val today = DateFormat.getDateInstance().format(Calendar.getInstance().time)
        val valuesPerDayPreference: String = SharedPrefUtils.getPreference(context, LATEST_SEVEN_DAYS, context.getString(R.string.latest_seven_days_default))
        val valuePerDay = valuesPerDayPreference.split(", ").toMutableList()
        if (today != lastKnownDate) {
            valuePerDay.add(currentCounter.toString())
            val lastSevenDays = valuePerDay.drop(if (valuePerDay.size > 7) valuePerDay.size - 7 else 0)
            with(SharedPrefUtils) {
                setPreference(context, LATEST_KNOWN_DATE, today)
                setPreference(context, MainActivity.CURRENT_COUNTER, 1)
                setPreference(context, LATEST_SEVEN_DAYS, lastSevenDays.joinToString())
            }
            LocalBroadcastManager.getInstance(context).sendBroadcast(Intent("counter-updated"))
        } else {
            SharedPrefUtils.setPreference(context, MainActivity.CURRENT_COUNTER, currentCounter + 1)
            LocalBroadcastManager.getInstance(context).sendBroadcast(Intent("counter-updated"))
        }
    }
}