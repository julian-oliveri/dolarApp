package com.example.dolarApp.mvvm.repository

import android.util.Log
import com.example.dolarApp.mvvm.model.ValueModel
import com.example.dolarApp.mvvm.model.ValueSearchBody
import com.example.dolarApp.mvvm.model.ValueSearchResponse
import com.example.dolarApp.mvvm.remote.DataSource

class DolarRepository(private val dataSource: DataSource) {
    suspend fun getDolarValue(): List<ValueModel> {
        return try {
            val response = dataSource.getDolarValue()
            response
        } catch (exception: Exception) {
            Log.e("error getValueDolar", exception.message.toString())
            throw exception
        }
    }

    suspend fun getCurrencyValue(): List<ValueModel> {
        return try {
            val response = dataSource.getCurrencyValue()
            response
        } catch (exception: Exception) {
            Log.e("error getValueDolar", exception.message.toString())
            throw exception
        }
    }


    suspend fun getValueSearch(body: ValueSearchBody): ValueSearchResponse {
        return try {
            val response = dataSource.getValueSearch(body)
            response
        } catch (exception: Exception) {
            Log.e("error getSearch", exception.message.toString())
            throw exception
        }
    }
}