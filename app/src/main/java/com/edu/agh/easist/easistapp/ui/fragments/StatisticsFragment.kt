package com.edu.agh.easist.easistapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.anychart.APIlib
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.core.cartesian.series.Line
import com.anychart.data.Set
import com.anychart.enums.Anchor
import com.anychart.enums.MarkerType
import com.anychart.enums.TooltipPositionMode
import com.anychart.graphics.vector.Stroke
import com.edu.agh.easist.easistapp.R
import com.edu.agh.easist.easistapp.logic.ResourceApiConntector
import com.edu.agh.easist.easistapp.models.DiaryEntry
import com.edu.agh.easist.easistapp.models.SymptomEntry
import com.edu.agh.easist.easistapp.utils.*
import kotlinx.android.synthetic.main.fragment_diary.*
import kotlinx.android.synthetic.main.fragment_diary.view.*
import kotlinx.android.synthetic.main.fragment_statistics.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList


class StatisticsFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private lateinit var diaryEntries: Collection<DiaryEntry>
    private lateinit var symptomsChartObject: AnyChartView
    private lateinit var moodAndSleepChartObject: AnyChartView
    private lateinit var symptomMap: Map<String, Collection<SymptomEntry>>

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_statistics, container, false)
        return view
    }

    override fun onStart() {
        super.onStart()
        showMenu(requireActivity(), true)
        if (getRole(activity) == ROLE_DOCTOR){
            showToolbar(requireActivity(), true)
            setToolbar(requireActivity(), getBrowsedUserFullName(activity)!!, getBrowsedUser(activity))
        }
        symptomsChartObject = symptomsChartView
        symptomsChartObject.setProgressBar(symptomsProgressBar)
        moodAndSleepChartObject = moodAndSleepChartView
        moodAndSleepChartObject.setProgressBar(moodAndSleepProgressBar)
        setSpinner()
    }

    private class DataEntry constructor(x: String?, value: Number?) : ValueDataEntry(x, value) {
        fun addValue(valueName: String, value: Number?) {
            setValue(valueName, value)
        }
    }

    private fun setSpinner(){
        val arguments = requireActivity().resources.getStringArray(R.array.statistics_spinner)
        val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                arguments
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        statisticSpinner.adapter = adapter
        statisticSpinner.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (position){
            0 -> {
                moodAndSleepChartObject.visibility = View.GONE
                moodAndSleepProgressBar.visibility = View.GONE
                symptomsChartObject.visibility = View.VISIBLE
                symptomsProgressBar.visibility = View.VISIBLE
                APIlib.getInstance().setActiveAnyChartView(symptomsChartObject)
                if (getRole(activity) == ROLE_PATIENT) {
                    getAllSymptomEntriesByDate(getUsername(activity)!!)
                } else {
                    getAllSymptomEntriesByDate(getBrowsedUser(activity)!!)
                }
            }
            1 -> {
                symptomsChartObject.visibility = View.GONE
                symptomsProgressBar.visibility = View.GONE
                moodAndSleepChartObject.visibility = View.VISIBLE
                moodAndSleepProgressBar.visibility = View.VISIBLE
                APIlib.getInstance().setActiveAnyChartView(moodAndSleepChartObject)
                if (getRole(activity) == ROLE_PATIENT) {
                    getAllDiaryEntriesByDate(getUsername(activity)!!)
                } else {
                    getAllDiaryEntriesByDate(getBrowsedUser(activity)!!)
                }
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    private fun createMoodAndSleepChart(){
        val cartesian = AnyChart.line()
        cartesian.animation(true)
        cartesian.padding(10.0, 20.0, 5.0, 20.0)

        cartesian.crosshair().enabled(true)
        cartesian.crosshair()
                .yLabel(true)
                .yStroke(null as Stroke?, null, null, null as String?, null as String?)

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT)
//        cartesian.title(requireActivity().getString(R.string.chart__mood_title))
        cartesian.yAxis(0).title(requireActivity().getString(R.string.chart__mood_legend))
        cartesian.xAxis(0).labels().padding(5.0, 5.0, 5.0, 5.0)

        val seriesData: MutableList<com.anychart.chart.common.dataentry.DataEntry> = ArrayList()
        for (entry in diaryEntries){
            val diaryDataEntry = DataEntry(parseDateToString(entry.date!!), entry.mood!! + 1)
            diaryDataEntry.addValue("value1", entry.sleepEntry!!.hourCount)
            seriesData.add(diaryDataEntry)
        }

        val set = Set.instantiate()
        set.data(seriesData)

        val seriesMappingMood = set.mapAs("{ x: 'x', value: 'value' }")
        val seriesMappingSleep = set.mapAs("{ x: 'x', value: 'value1' }")

        val seriesMood: Line = cartesian.line(seriesMappingMood)
        seriesMood.name(requireActivity().getString(R.string.chart__mood_series))
        seriesMood.hovered().markers().enabled(true)
        seriesMood.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4.0)
        seriesMood.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5.0)
                .offsetY(5.0)

        val seriesSleep: Line = cartesian.line(seriesMappingSleep)
        seriesSleep.name(requireActivity().getString(R.string.chart__sleep_series))
        seriesSleep.hovered().markers().enabled(true)
        seriesSleep.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4.0)
        seriesSleep.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5.0)
                .offsetY(5.0)

        cartesian.legend().enabled(true)
        cartesian.legend().fontSize(13.0)
        cartesian.legend().padding(0.0, 0.0, 10.0, 0.0)

        moodAndSleepChartObject.setChart(cartesian)
    }

    private fun getAllSymptomEntriesByDate(username: String){
        if (this::symptomMap.isInitialized) {
            createSymptomChart()
            return
        }

        GlobalScope.launch(Dispatchers.Main) {
            try {
                if (getToken(activity) == null)
                    throw java.lang.Exception("Unauthorized")

                val response = ResourceApiConntector.apiClient
                        .getSymptomEntriesByDate(
                                getToken(activity)!!,
                                username
                        )

                Timber.d(response.toString())
                if (response.isSuccessful && response.body() != null) {
                    symptomMap = TreeMap(response.body()!!)
                    createSymptomChart()
                } else {
                    showToast(
                            activity,
                            "Error: ${response.message()}"
                    )
                }
            } catch (e: Exception) {
                showToast(
                        activity,
                        "Error while connecting: ${e.message}"
                )
            }
        }
    }

    private fun createSymptomChart(){
        val cartesian = AnyChart.line()
        cartesian.animation(true)
        cartesian.padding(10.0, 20.0, 5.0, 20.0)

        cartesian.crosshair().enabled(true)
        cartesian.crosshair()
            .yLabel(true)
            .yStroke(null as Stroke?, null, null, null as String?, null as String?)

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT)
//        cartesian.title(requireActivity().getString(R.string.chart__symptom_title))
        cartesian.yAxis(0).title(requireActivity().getString(R.string.chart__symptom_legend))
        cartesian.xAxis(0).labels().padding(5.0, 5.0, 5.0, 5.0)

        val seriesData: MutableList<com.anychart.chart.common.dataentry.DataEntry> = ArrayList()
        for ((key, value) in symptomMap){
            val symptomDataEntry = DataEntry(key, value.elementAt(0).intensity)
            for (i in 1 until value.size){
                symptomDataEntry.addValue("value$i", value.elementAt(i).intensity)
            }
            seriesData.add(symptomDataEntry)
        }

        val set = Set.instantiate()
        set.data(seriesData)

        for ((i, entry) in symptomMap[symptomMap.keys.first()]!!.withIndex()){
            val seriesMapping = if (i == 0) {
                set.mapAs("{ x: 'x', value: 'value' }")
            } else {
                set.mapAs("{ x: 'x', value: 'value$i' }")
            }
            val series: Line = cartesian.line(seriesMapping)
            series.name(entry.symptom!!.name)
            series.hovered().markers().enabled(true)
            series.hovered().markers()
                    .type(MarkerType.CIRCLE)
                    .size(4.0)
            series.tooltip()
                    .position("right")
                    .anchor(Anchor.LEFT_CENTER)
                    .offsetX(5.0)
                    .offsetY(5.0)
        }

        cartesian.legend().enabled(true)
        cartesian.legend().fontSize(13.0)
        cartesian.legend().padding(0.0, 0.0, 10.0, 0.0)

        symptomsChartObject.setChart(cartesian)
    }

    private fun getAllDiaryEntriesByDate(username: String){
        if (this::diaryEntries.isInitialized) {
            createMoodAndSleepChart()
            return
        }

        GlobalScope.launch(Dispatchers.Main) {
            try {
                if (getToken(activity) == null)
                    throw java.lang.Exception("Unauthorized")

                val response = ResourceApiConntector.apiClient
                        .getDiaryEntries(
                                getToken(activity)!!,
                                username
                        )

                Timber.d(response.toString())
                if (response.isSuccessful && response.body() != null) {
                    diaryEntries = response.body()!!
                    createMoodAndSleepChart()
                } else {
                    showToast(
                            activity,
                            "Error: ${response.message()}"
                    )
                }
            } catch (e: Exception) {
                showToast(
                        activity,
                        "Error while connecting: ${e.message}"
                )
            }
        }
    }

}
