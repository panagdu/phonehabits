package uk.panasys.phonehabits.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import uk.panasys.phonehabits.MainActivity
import uk.panasys.phonehabits.utils.SharedPrefUtils
import java.text.DateFormat
import java.util.*

class ScreenOnReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val count = SharedPrefUtils.getIntegerPreference(context, MainActivity.CURRENT_COUNTER, 0)
        increaseOrResetCounter(context, count)
    }

    private fun increaseOrResetCounter(context: Context, countParam: Int) {
        var count = countParam
        val lastDate = SharedPrefUtils.getStringPreference(context, "LATEST_KNOWN_DATE", "")
        val today = DateFormat.getDateInstance().format(Calendar.getInstance().time)
        val valuesPerDay: String = SharedPrefUtils.getStringPreference(context, "LATEST_SEVEN_DAYS", "0, 0, 0, 0, 0, 0, 0")
        val valuePerDayList = valuesPerDay.split(", ").toMutableList()
        if (today != lastDate) {
            SharedPrefUtils.setStringPreference(context, "LATEST_KNOWN_DATE", today)
            valuePerDayList.add(count.toString())
            val lastSevenDays = valuePerDayList.drop(if (valuePerDayList.size > 7) valuePerDayList.size - 7 else 0)
            SharedPrefUtils.setIntegerPreference(context, MainActivity.CURRENT_COUNTER, 1)
            SharedPrefUtils.setStringPreference(context, "LATEST_SEVEN_DAYS", lastSevenDays.joinToString())
        } else {
            SharedPrefUtils.setIntegerPreference(context, MainActivity.CURRENT_COUNTER, ++count)
        }
    }
}