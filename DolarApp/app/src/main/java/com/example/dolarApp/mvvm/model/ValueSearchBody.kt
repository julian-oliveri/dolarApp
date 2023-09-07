package com.example.dolarApp.mvvm.model

import com.google.gson.annotations.SerializedName

data class ValueSearchBody (
    @SerializedName("id")
    val id: String,
    @SerializedName("fechaInicio")
    val fechaInicio: String,
    @SerializedName("fechaFin")
    val fechaFin: String
)

