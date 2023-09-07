package com.example.dolarApp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dolarApp.mvvm.model.ValueModel
import com.example.jubblerdesafiotecnico.R

class CurrencyValueAdapter(private var valueList: List<ValueModel>) : RecyclerView.Adapter<CurrencyValueViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyValueViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CurrencyValueViewHolder(layoutInflater.inflate(R.layout.currency_table_item, parent, false))
    }

    override fun onBindViewHolder(holder: CurrencyValueViewHolder, position: Int) {
        val item = valueList[position]
        holder.render(item)
    }

    override fun getItemCount(): Int = valueList.size

    fun setListData(list: List<ValueModel>) {
        valueList = list
    }

}