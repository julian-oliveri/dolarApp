package com.example.dolarApp.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.dolarApp.mvvm.model.ValueModel
import com.example.jubblerdesafiotecnico.R

class CurrencyValueViewHolder(view: View): ViewHolder(view) {
    val currTitle = view.findViewById<TextView>(R.id.currency_name)
    val sellValue = view.findViewById<TextView>(R.id.currency_sell_value)
    val buyValue = view.findViewById<TextView>(R.id.currency_buy_value)


    fun render(value: ValueModel) {
        currTitle.text = value.name
        sellValue.text = value.sell
        buyValue.text = value.buy
    }
}