package uk.panasys.phonehabits.activities

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
        counter.text = SharedPrefUtils.getPreference(activity, MainActivity.CURRENT_COUNTER, 1).toString()

        val chart = activity.findViewById<View>(R.id.chart) as BarChart
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
        val valuesPerDay = SharedPrefUtils.getPreference(activity, "LATEST_SEVEN_DAYS", "0, 0, 0, 0, 0, 0, 0")
        val valuePerDayList = valuesPerDay.split(", ").toMutableList()
        return valuePerDayList.mapIndexed { i, s -> BarEntry(i.toFloat(), s.toFloat()) }.toList()
    }
}
