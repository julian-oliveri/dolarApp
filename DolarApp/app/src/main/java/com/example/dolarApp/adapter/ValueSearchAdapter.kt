package com.example.dolarApp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dolarApp.mvvm.model.ValueSearchModel
import com.example.jubblerdesafiotecnico.R

class ValueSearchAdapter (private var valueList: List<ValueSearchModel>) : RecyclerView.Adapter<ValueSearchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ValueSearchViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ValueSearchViewHolder(layoutInflater.inflate(R.layout.timeline_table_item, parent, false))
    }

    override fun onBindViewHolder(holder: ValueSearchViewHolder, position: Int) {
        val item = valueList[position]
        holder.render(item, position)
    }

    override fun getItemCount(): Int = valueList.size

    fun setListData(list: List<ValueSearchModel>) {
        valueList = list
    }

}