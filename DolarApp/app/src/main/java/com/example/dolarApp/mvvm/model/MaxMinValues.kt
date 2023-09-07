package com.example.dolarApp.mvvm.model

import com.google.gson.annotations.SerializedName

data class MaxMinValues (
        @SerializedName("segundoValor")
        val sellList: MaxValueModel,
        @SerializedName("tercerValor")
        val buyList: MaxValueModel,
        )