package com.example.dolarApp.mvvm.model

import com.google.gson.annotations.SerializedName

data class ValueSearchModel (
  @SerializedName("compra")
  val buy: String,
  @SerializedName("venta")
  val sell: String,
  @SerializedName("fecha")
  val date: String
         )