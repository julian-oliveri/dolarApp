package com.example.dolarApp.mvvm.remote

import android.util.Log
import com.example.dolarApp.mvvm.model.GetDolarValueResponse
import com.example.dolarApp.mvvm.model.ValueModel
import com.example.dolarApp.mvvm.model.ValueSearchBody
import com.example.dolarApp.mvvm.model.ValueSearchResponse
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DataSource(private val webService: WebService) {

    suspend fun getDolarValue(): List<ValueModel> {
        return withContext(Dispatchers.IO) {
            val response = webService.getDolarValue().body()?: listOf()
            var cleanResponse: MutableList<ValueModel> = mutableListOf()
            val gson = GsonBuilder().create()
            for (item: JsonElement in response) {
                try {
                    val myItem = gson.fromJson(item, GetDolarValueResponse::class.java)
                    cleanResponse.add(myItem.value)
                } catch (error: java.lang.Exception) {
                    Log.i("error", error.toString())
                }
            }
            cleanResponse
        }
    }

    suspend fun getCurrencyValue(): List<ValueModel> {
        return withContext(Dispatchers.IO) {
            val response = webService.getCurrencyValue().body()?: listOf()
            var cleanResponse: MutableList<ValueModel> = mutableListOf()
            val gson = GsonBuilder().create()
            for (item: JsonElement in response) {
                try {
                    val myItem = gson.fromJson(item, GetDolarValueResponse::class.java)
                    cleanResponse.add(myItem.value)
                } catch (error: java.lang.Exception) {
                    Log.i("error", error.toString())
                }
            }
            cleanResponse
        }
    }


    suspend fun getValueSearch(body: ValueSearchBody): ValueSearchResponse {
        return withContext(Dispatchers.IO) {
            val params = mutableMapOf<String, String>()
            params.put("id", body.id)
            params.put("fechaInicio", body.fechaInicio)
            params.put("fechaFin", body.fechaFin)
            val response = webService.getSearchDate(params).body()
            response!!
        }
    }
}