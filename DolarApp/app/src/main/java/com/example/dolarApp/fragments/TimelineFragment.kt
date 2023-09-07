package com.example.dolarApp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dolarApp.MainActivity
import com.example.dolarApp.adapter.ValueSearchAdapter
import com.example.dolarApp.database.entities.ValueEntity
import com.example.dolarApp.dialog.CustomDialog
import com.example.dolarApp.mvvm.DolarModelFactory
import com.example.dolarApp.mvvm.DolarViewModel
import com.example.dolarApp.mvvm.model.ValueSearchBody
import com.example.dolarApp.mvvm.model.ValueSearchModel
import com.example.dolarApp.mvvm.remote.DataSource
import com.example.dolarApp.mvvm.remote.RetrofitClient
import com.example.dolarApp.mvvm.repository.DolarRepository
import com.example.jubblerdesafiotecnico.R
import com.example.jubblerdesafiotecnico.databinding.FragmentTimelineBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import kotlinx.coroutines.launch
import java.time.LocalDate

class TimelineFragment : Fragment() {

private var _binding: FragmentTimelineBinding? = null
    private val binding get() = _binding!!
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: ValueSearchAdapter
    lateinit var tabLayout: TabLayout

    lateinit var lineChart: LineChart
    var lineData: LineData = LineData()
    var xAxis: XAxis = XAxis()

    lateinit var dbData: MutableList<ValueEntity>

    var isSearch = false
    var tabType = "COMPRA"

    val viewModel by viewModels<DolarViewModel> {
        DolarModelFactory(
            DolarRepository(
                DataSource(
                    RetrofitClient.webService
                )
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTimelineBinding.inflate(inflater, container, false)
        recyclerView = binding.reciclerCurrency
        tabLayout = binding.tabLy
        lineChart = binding.lineChart
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getDataDb()
        creatGraphDb("COMPRA")
        getValuesDb(false, "", "")

        tabLayout.setOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when(tab.text) {
                    "COMPRA" -> {
                        tabType = "COMPRA"
                        handleChangeTab()
                    }
                    "VENTA" -> {
                        tabType = "VENTA"
                        handleChangeTab()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        binding.buttonSecond.setOnClickListener {
            CustomDialog(context!!).show(::getHistory)

        }

        viewModel.searchValueList.observe(viewLifecycleOwner) { searchResponse ->
            creatGraph(tabType, searchResponse.valuesList, binding.valueTitle.text.toString())
            adapter.setListData(searchResponse.valuesList)
            adapter.notifyDataSetChanged()
        }
    }

    fun handleChangeTab() {
        if (isSearch == true){
            viewModel.searchValueList.value?.let { creatGraph(tabType, it.valuesList, binding.valueTitle.text.toString()) }
        } else {
            creatGraphDb(tabType)
        }
    }

    fun clearGraphData() {
        var dataSetNumber = lineData.dataSets.count()

        while (dataSetNumber > -1) {
            lineData.removeDataSet(0)
            dataSetNumber --
        }
        xAxis.valueFormatter = null

    }

    fun getDataDb() {
        dbData = (activity as MainActivity).getValues()
    }

    fun creatGraphDb(type: String) {
        clearGraphData()

        val offValueList = mutableListOf<Entry>()
        val bluValueList = mutableListOf<Entry>()
        val dateList = mutableListOf<String>()

        var pos = 0

        if (dbData.isEmpty()) return

        for (item in dbData) {
            var blueEntry: Entry
            var offEntry: Entry

            when (type) {
                "COMPRA" -> {
                    offEntry = Entry(pos.toFloat(), item.buy.replace(',', '.').toFloat())
                    blueEntry = Entry(pos.toFloat(), item.buyBlu.replace(',', '.').toFloat())
                }
                "VENTA" -> {
                    offEntry = Entry(pos.toFloat(), item.sell.replace(',', '.').toFloat())
                    blueEntry = Entry(pos.toFloat(), item.sellBlu.replace(',', '.').toFloat())
                }
                else -> {
                    offEntry = Entry(pos.toFloat(), item.buy.replace(',', '.').toFloat())
                    blueEntry = Entry(pos.toFloat(), item.buyBlu.replace(',', '.').toFloat())
                }
            }
            offValueList.add(offEntry)
            bluValueList.add(blueEntry)
            dateList.add(item.date)
            pos += 1
        }

        addMyDataset(offValueList, "Dolar Oficial", R.color.money_green)
        addMyDataset(bluValueList, "Dolar Blue", R.color.night_blue)
        addFormatter(dateList)

        changeData()
    }

    fun creatGraph(type: String, list: List<ValueSearchModel>, label: String) {
        clearGraphData()

        val valueList = mutableListOf<Entry>()
        val dateList = mutableListOf<String>()

        var pos = 0

        for (item in list) {
            var entry: Entry

            when (type) {
                "COMPRA" -> {
                    entry = Entry(pos.toFloat(), item.buy.replace(',', '.').toFloat())
                }
                "VENTA" -> {
                    entry = Entry(pos.toFloat(), item.sell.replace(',', '.').toFloat())
                }
                else -> {
                    entry = Entry(pos.toFloat(), item.buy.replace(',', '.').toFloat())
                }
            }
            valueList.add(entry)
            dateList.add(item.date)
            pos += 1
        }

        addMyDataset(valueList, label, R.color.money_green)
        addFormatter(dateList)

        changeData()
    }

    fun getHistory(position: Int, from: String, to: String) {
        val optionsVal = resources.getStringArray(R.array.options_value)
        val options = resources.getStringArray(R.array.options_picker)

        binding.valueTitle.text = options[position]
        if (position == 0) {
            isSearch = false
            getValuesDb(true, to, from)
            creatGraphDb(tabType)
        } else {
            isSearch = true
            lifecycleScope.launch {
                val data = ValueSearchBody(
                    optionsVal[position],
                    from,
                    to
                )
                viewModel.fetchGetValueSearch(data)
            }
        }
    }


    fun addMyDataset(valueList: MutableList<Entry>, label: String, color: Int) {
        var lineDataSet = LineDataSet(valueList, label)
        lineDataSet.color = resources.getColor(color)
        lineDataSet.setCircleColor(resources.getColor(color))
        lineDataSet.lineWidth = 4f
        lineDataSet.circleRadius = 6f
        lineDataSet.valueTextSize = 10f
        lineDataSet.formSize = 20f
        lineData.addDataSet(lineDataSet)
    }

    fun addFormatter(dateList: MutableList<String>) {
        xAxis.valueFormatter = null

        val formatter: ValueFormatter = object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase): String {
                if (value.toInt() < dateList.count() && value.toInt() > 0) {
                    return dateList[value.toInt()]
                }
                return ""
            }
        }
        xAxis.valueFormatter = formatter
    }

    fun changeData() {
        lineChart.invalidate()
        xAxis = lineChart.getXAxis()
        xAxis.granularity = 1f
        lineChart.data = lineData
        lineChart.description.isEnabled = false
        lineChart.notifyDataSetChanged()
    }

    fun getValuesDb(order: Boolean, to: String, from: String) {
        val valueList = mutableListOf<ValueSearchModel>()
        for (item in dbData) {
            if (order) {
                if (compareDates(from, to, item.date)) {
                    valueList.add(ValueSearchModel(item.buy, item.sell, item.date))
                }
            } else {
                valueList.add(ValueSearchModel(item.buy, item.sell, item.date))
            }
        }
        adapter = ValueSearchAdapter(valueList)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }

    fun compareDates(from: String,to: String , date: String): Boolean {
//         ddmmyyyy fechaInicio/fechaFinal
//         yyyy-mm-dd viene en mi item

        val dia = from.substring(0,2)
        val mes = from.substring(2,4)
        val year = from.substring(4)
        val dateFrom = LocalDate.parse("$year-$mes-$dia")

        val dia2 = to.substring(0,2)
        val mes2 = to.substring(2,4)
        val year2 = to.substring(4)
        val dateTo = LocalDate.parse("$year2-$mes2-$dia2")

        val dateItem = LocalDate.parse(date)

        if (dateItem.isAfter(dateFrom) && !dateItem.isEqual(dateFrom)) return false
        if (dateItem.isBefore(dateTo) && !dateItem.isEqual(dateTo)) return false

        return true
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}