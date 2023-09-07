package com.example.dolarApp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dolarApp.MainActivity
import com.example.dolarApp.adapter.CurrencyValueAdapter
import com.example.dolarApp.database.entities.ValueEntity
import com.example.dolarApp.mvvm.DolarModelFactory
import com.example.dolarApp.mvvm.DolarViewModel
import com.example.dolarApp.mvvm.model.ValueModel
import com.example.dolarApp.mvvm.remote.DataSource
import com.example.dolarApp.mvvm.remote.RetrofitClient
import com.example.dolarApp.mvvm.repository.DolarRepository
import com.example.jubblerdesafiotecnico.R
import com.example.jubblerdesafiotecnico.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class HomeFragment : Fragment() {

private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var valueList : List<ValueModel> = listOf()
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: CurrencyValueAdapter
    lateinit var tabLayout: TabLayout

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
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        recyclerView = binding.reciclerCurrency
        tabLayout = binding.tabLy
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEvents()
        getDollarValue()

        viewModel.dolarValueList.observe(viewLifecycleOwner) { dollarList ->
            valueList = dollarList
            adapter.setListData(valueList)
            adapter.notifyDataSetChanged()
            hideLoading()
        }
    }

    private fun getDollarValue() {
        showLoading()
        lifecycleScope.launch {
            viewModel.fetchGetDolarValue()
        }
    }

    private fun getCurrencyValue() {
        showLoading()
        lifecycleScope.launch {
            viewModel.fetchGetCurrencyValue()
        }
    }

    private fun insertInDb() {
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        var date = LocalDate.now().format(formatter)

        val oficial = valueList.find { it.name == "Dolar Oficial" }
        val blue = valueList.find { it.name == "Dolar Blue" }

        if (oficial != null && blue != null){
            (activity as MainActivity).insertValue(
                ValueEntity(
                    buy = oficial.buy,
                    sell = oficial.sell,
                    buyBlu = blue.buy,
                    sellBlu = blue.sell,
                    date = date
                )
            )
        }
    }


    private fun setupEvents() {
        adapter = CurrencyValueAdapter(valueList)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        binding.buttonUpdt.setOnClickListener {
            tabLayout.getTabAt(0)!!.select()
            getDollarValue()
            insertInDb()
        }

        binding.buttonTimeline.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }


        tabLayout.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when(tab.position) {
                    0 -> {
                        getDollarValue()
                    }
                    1 -> {
                        getCurrencyValue()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    private fun showLoading() {
        binding.progressbar.visibility = View.VISIBLE
        binding.reciclerCurrency.visibility = View.GONE
    }

    private fun hideLoading() {
        binding.progressbar.visibility = View.GONE
        binding.reciclerCurrency.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
    }