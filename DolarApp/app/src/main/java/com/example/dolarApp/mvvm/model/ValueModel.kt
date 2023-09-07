package com.example.dolarApp.mvvm.model

import com.google.gson.annotations.SerializedName

data class ValueModel (
    @SerializedName("compra")
    val buy: String,
    @SerializedName("venta")
    val sell: String,
    @SerializedName("nombre")
    val name: String,
)