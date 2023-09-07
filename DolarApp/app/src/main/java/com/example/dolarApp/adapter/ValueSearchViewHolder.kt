package com.example.dolarApp.adapter

import android.view.View
import android.widget.TableRow
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dolarApp.mvvm.model.ValueSearchModel
import com.example.jubblerdesafiotecnico.R

class ValueSearchViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val header = view.findViewById<TableRow>(R.id.header)
    val currDate = view.findViewById<TextView>(R.id.currency_date)
    val sellValue = view.findViewById<TextView>(R.id.currency_sell_value)
    val buyValue = view.findViewById<TextView>(R.id.currency_buy_value)


    fun render(value: ValueSearchModel, pos: Int) {
        if (pos == 0) header.visibility = View.VISIBLE
        currDate.text = value.date
        sellValue.text = value.sell
        buyValue.text = value.buy
    }
}