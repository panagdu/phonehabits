package uk.panasys.phonehabits

import android.graphics.Color
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
        val entries = prepareDataForChart()

        val barDataSet = BarDataSet(entries, context.getString(R.string.last_days))
        barDataSet.valueTextSize = 14f
        barDataSet.color = Color.argb(255, 255, 175, 64)
        barDataSet.valueFormatter = IntValueFormatter()

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
        return listOf(
                BarEntry(6f, 25f),
                BarEntry(5f, 34f),
                BarEntry(4f, 67f),
                BarEntry(3f, 15f),
                BarEntry(2f, 29f),
                BarEntry(1f, 43f),
                BarEntry(0f, 56f)
        )
    }
}
