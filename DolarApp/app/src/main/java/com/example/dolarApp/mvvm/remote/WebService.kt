package com.example.dolarApp.mvvm.remote

import com.example.dolarApp.mvvm.model.ValueSearchResponse
import com.google.gson.JsonArray
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.*

interface WebService {
    @GET("api/api.php?type=valoresprincipales")
    suspend fun getDolarValue() : Response<JsonArray>

    @GET("api/api.php?type=cotizador")
    suspend fun getCurrencyValue(): Response<JsonArray>

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("adm/api/estadisticas/?type=getHistoricoAndPrincipales")
    @FormUrlEncoded
    suspend fun getSearchDate(@FieldMap params: Map<String,String> ) : Response<ValueSearchResponse>
}