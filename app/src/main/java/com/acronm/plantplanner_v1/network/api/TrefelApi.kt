package com.acronm.plantplanner_v1.network.api

import com.acronm.plantplanner_v1.database.AppDatabase
import com.acronm.plantplanner_v1.model.PlantResponse
import com.acronm.plantplanner_v1.model.TrefelPlant
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Query

interface TrefelApi {
    @GET("plants/search")
    fun searchPlantsFromApi(
        @Query("token") apiKey: String,
        @Query("q") query: String,
    ): Call<PlantResponse>
}






