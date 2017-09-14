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

    override fun onReceive(context: Context, intent: Intent) {
        val count = SharedPrefUtils.getPreference(context, MainActivity.CURRENT_COUNTER, 0)
        increaseOrResetCounter(context, count)
    }


    private fun increaseOrResetCounter(context: Context, count: Int) {
        val lastKnownDate = SharedPrefUtils.getPreference(context, LATEST_KNOWN_DATE, "")
        val today = DateFormat.getDateInstance().format(Calendar.getInstance().time)
        val valuesPerDay: String = SharedPrefUtils.getPreference(context, LATEST_SEVEN_DAYS, context.getString(R.string.latest_seven_days_default))
        val valuePerDayList = valuesPerDay.split(", ").toMutableList()
        if (today != lastKnownDate) {
            SharedPrefUtils.setPreference(context, LATEST_KNOWN_DATE, today)
            valuePerDayList.add(count.toString())
            val lastSevenDays = valuePerDayList.drop(if (valuePerDayList.size > 7) valuePerDayList.size - 7 else 0)
            SharedPrefUtils.setPreference(context, MainActivity.CURRENT_COUNTER, 1)
            SharedPrefUtils.setPreference(context, LATEST_SEVEN_DAYS, lastSevenDays.joinToString())
            LocalBroadcastManager.getInstance(context).sendBroadcast(Intent("counter-updated"))
        } else {
            SharedPrefUtils.setPreference(context, MainActivity.CURRENT_COUNTER, count + 1)
            LocalBroadcastManager.getInstance(context).sendBroadcast(Intent("counter-updated"))
        }
    }
}