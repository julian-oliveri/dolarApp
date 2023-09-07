package com.example.dolarApp.mvvm.model

import com.google.gson.annotations.SerializedName

data class MaxValueModel (
    @SerializedName("nombre")
    val name: String,
    @SerializedName("menorValor")
    val lowVal: String,
    @SerializedName("menorFecha")
    val lowDate: String,
    @SerializedName("mayorValor")
    val HighVal: String,
    @SerializedName("mayorFecha")
    val HighDate: String,
        )