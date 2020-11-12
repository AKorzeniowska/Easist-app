package com.edu.agh.easist.easistapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.charts.Cartesian
import com.anychart.core.cartesian.series.RangeColumn
import com.anychart.data.Mapping
import com.anychart.data.Set
import com.edu.agh.easist.easistapp.R


class StatisticsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_statistics, container, false)
        val anyChartView: AnyChartView = view.findViewById(R.id.any_chart_view)
        anyChartView.setProgressBar(view.findViewById(R.id.progress_bar))

        val cartesian: Cartesian = AnyChart.cartesian()

        cartesian.title("Coastal Water Temperature \\nin London vs Edinburgh in 2015 (°C)")

        val data: MutableList<DataEntry> = ArrayList()
        data.add(CustomDataEntry("Jan", 5.8, 7.9, 6.1, 8.9))
        data.add(CustomDataEntry("Feb", 4.6, 6.1, 5.5, 8.2))
        data.add(CustomDataEntry("Mar", 5.9, 8.1, 5.9, 8.1))
        data.add(CustomDataEntry("Apr", 7.8, 10.7, 7.1, 9.8))
        data.add(CustomDataEntry("May", 10.5, 13.7, 8.3, 10.7))
        data.add(CustomDataEntry("June", 13.8, 17, 10.7, 14.5))
        data.add(CustomDataEntry("July", 16.5, 18.5, 12.3, 16.7))
        data.add(CustomDataEntry("Aug", 17.8, 19, 14, 16.3))
        data.add(CustomDataEntry("Sep", 15.4, 17.8, 13.7, 15.3))
        data.add(CustomDataEntry("Oct", 12.7, 15.3, 12.3, 14.4))
        data.add(CustomDataEntry("Nov", 9.8, 13, 12.9, 10.7))
        data.add(CustomDataEntry("Dec", 9, 10.1, 8.2, 11.1))

        val set = Set.instantiate()
        set.data(data)
        val londonData: Mapping = set.mapAs("{ x: 'x', high: 'londonHigh', low: 'londonLow' }")
        val edinburgData: Mapping =
            set.mapAs("{ x: 'x', high: 'edinburgHigh', low: 'edinburgLow' }")

        val columnLondon: RangeColumn = cartesian.rangeColumn(londonData)
        columnLondon.name("London")

        val columnEdinburg: RangeColumn = cartesian.rangeColumn(edinburgData)
        columnEdinburg.name("Edinburgh")

        cartesian.xAxis(true)
        cartesian.yAxis(true)

        cartesian.yScale()
            .minimum(4.0)
            .maximum(20.0)

        cartesian.legend(true)

        cartesian.yGrid(true)
            .yMinorGrid(true)

        cartesian.tooltip().titleFormat("{%SeriesName} ({%x})")

        anyChartView.setChart(cartesian)
        return view
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
    private class CustomDataEntry(
        x: String?,
        edinburgHigh: Number?,
        edinburgLow: Number?,
        londonHigh: Number?,
        londonLow: Number?
    ) :
        DataEntry() {
        init {
            setValue("x", x)
            setValue("edinburgHigh", edinburgHigh)
            setValue("edinburgLow", edinburgLow)
            setValue("londonHigh", londonHigh)
            setValue("londonLow", londonLow)
        }
    }

}
