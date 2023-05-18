package com.acronm.plantplanner_v1.network.api

import android.content.Context
import com.acronm.plantplanner_v1.R
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.Properties

interface PlantApiService {

//    @GET("plants")
//    suspend fun searchPlants(
//        @Query("token") apiKey: String,
//        @Query("filter[common_name]") query: String
//    ): Response<List<Plant>>

    object ConfigManager {
        private const val TAG = "configManager"

        fun getApiKey(context: Context): String {
            val properties = Properties()
            val assetManager = context.resources.openRawResource(R.raw.config)
            properties.apply {
                load(assetManager)
            }
            val apiKey = properties.getProperty("TREFEL_API_KEY", "default_value")

            return apiKey
        }
    }


    object RetrofitClient {
        private const val BASE_URL = "https://trefle.io/api/v1/"

        val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}