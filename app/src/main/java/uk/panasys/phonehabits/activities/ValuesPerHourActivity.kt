package uk.panasys.phonehabits.activities

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.github.mikephil.charting.charts.HorizontalBarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IValueFormatter
import com.github.mikephil.charting.utils.ViewPortHandler
import uk.panasys.phonehabits.R
import uk.panasys.phonehabits.utils.SharedPrefUtils

class ValuesPerHourActivity : AppCompatActivity() {

    private val activity = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_values_per_day)
        val chart = findViewById<View>(R.id.valuesPerDayChart) as HorizontalBarChart
        chart.renderBarChart()
    }

    private fun HorizontalBarChart.renderBarChart() {
        val entries = prepareDataForChart()
        val barDataSet = BarDataSet(entries, activity.getString(R.string.values_per_hour))
        with(barDataSet) {
            valueTextSize = 14f
            color = Color.argb(255, 255, 175, 64)
            valueFormatter = IValueFormatter { value: Float,
                                               entry: Entry?,
                                               dataSetIndex: Int,
                                               viewPortHandler: ViewPortHandler? ->
                value.toInt().toString()
            }
        }

        val barData = BarData(barDataSet)
        this.data = barData
        this.setPinchZoom(false)
        this.setDrawGridBackground(false)
        this.description.isEnabled = false
        this.axisLeft.isEnabled = false
        this.xAxis.isEnabled = true
        this.xAxis.labelRotationAngle = 45f
        this.xAxis.setLabelCount(24, true)
        this.xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
        this.axisRight.isEnabled = false
        this.invalidate()
    }

    private fun prepareDataForChart(): List<BarEntry> {
        val valuesPerDay = SharedPrefUtils.getPreference(activity, "VALUES_PER_HOUR", activity.getString(R.string.values_per_hour_default))
        val valuePerDayList = valuesPerDay.split(", ").toMutableList()
        return valuePerDayList.mapIndexed { i, s -> BarEntry(i.toFloat(), s.toFloat()) }.toList()
    }
}
