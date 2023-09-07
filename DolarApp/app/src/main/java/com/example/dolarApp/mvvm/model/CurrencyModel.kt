package com.example.dolarApp.mvvm.model

import com.google.gson.annotations.SerializedName

data class CurrencyModel (
    @SerializedName("id")
    val id: Int,
    @SerializedName("nombre")
    val name: String,
)