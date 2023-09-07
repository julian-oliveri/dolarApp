package com.example.dolarApp.mvvm.model

import com.google.gson.annotations.SerializedName

data class ValueSearchResponse (
    @SerializedName("valoresHistoricos")
    val valuesList: List<ValueSearchModel>,
    @SerializedName("valoresPrincipales")
    val minMax: MaxMinValues

)