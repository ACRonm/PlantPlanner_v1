package com.acronm.plantplanner_v1.network.api

import android.content.Context
import android.util.Log
import com.acronm.plantplanner_v1.R
import java.util.Properties

class PlantApiService {
    object PlantApi {
        private const val BASE_URL = "https://trefle.io/api/v1/"
        private const val TAG = "plantApi"


    }

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
}