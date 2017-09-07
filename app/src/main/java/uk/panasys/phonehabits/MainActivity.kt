package uk.panasys.phonehabits

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import uk.panasys.phonehabits.chart.IntValueFormatter


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val chart = findViewById<View>(R.id.chart) as BarChart
        chart.renderBarChart()

    }

    fun BarChart.renderBarChart() {
        val entries = listOf(
                BarEntry(0f, 1f),
                BarEntry(1f, 2f),
                BarEntry(2f, 3f),
                BarEntry(3f, 4f),
                BarEntry(4f, 5f),
                BarEntry(5f, 6f),
                BarEntry(6f, 7f)
        )

        val barDataSet = BarDataSet(entries, context.getString(R.string.last_days))
        barDataSet.valueTextSize = 14f
        barDataSet.valueFormatter = IntValueFormatter()
        val barData = BarData(barDataSet)
        this.setPinchZoom(false)
        this.setDrawGridBackground(false)
        this.description.isEnabled = false
        this.axisLeft.isEnabled = false
        this.xAxis.isEnabled = false
        this.axisRight.isEnabled = false
        this.data = barData

        this.invalidate()
    }
}
