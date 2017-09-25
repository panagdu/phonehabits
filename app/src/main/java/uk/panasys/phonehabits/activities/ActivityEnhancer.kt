package uk.panasys.phonehabits.activities

import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import uk.panasys.phonehabits.R
import uk.panasys.phonehabits.chart.IntValueFormatter
import uk.panasys.phonehabits.utils.SharedPrefUtils

class ActivityEnhancer(private val activity: AppCompatActivity) {

    fun updateCounterAndChart() {
        val counter: TextView = activity.findViewById(R.id.currentCounter)
        val timesText: TextView = activity.findViewById(R.id.times)
        counter.text = SharedPrefUtils.getPreference(activity, MainActivity.CURRENT_COUNTER, 1).toString()
        timesText.text = if (counter.text == "1") activity.resources.getString(R.string.time_today) else activity.resources.getString(R.string.times_today)
        val chart = activity.findViewById<View>(R.id.sevenDaysChart) as BarChart
        chart.renderBarChart()
    }

    private fun BarChart.renderBarChart() {
        val entries = prepareDataForChart()
        val barDataSet = BarDataSet(entries, activity.getString(R.string.last_days))

        with(barDataSet) {
            valueTextSize = 14f
            color = Color.argb(255, 255, 175, 64)
            valueFormatter = IntValueFormatter()
        }

        val barData = BarData(barDataSet)
        this.data = barData

        this.setPinchZoom(false)
        this.setDrawGridBackground(false)
        this.description.isEnabled = false
        this.axisLeft.isEnabled = false
        this.xAxis.isEnabled = false
        this.axisRight.isEnabled = false
        this.invalidate()
    }

    private fun prepareDataForChart(): List<BarEntry> {
        val valuesPerDay = SharedPrefUtils.getPreference(activity, "LATEST_SEVEN_DAYS", activity.getString(R.string.latest_seven_days_default))
        val valuePerDayList = valuesPerDay.split(", ").toMutableList()
        return valuePerDayList.mapIndexed { i, s -> BarEntry(i.toFloat(), s.toFloat()) }.toList()
    }
}
