package com.acronm.plantplanner_v1.config

import android.content.Context
import com.acronm.plantplanner_v1.R
import java.util.Properties

object ConfigManager {

    fun getApiKey(context: Context): String {
        val properties = Properties()
        val assetManager = context.resources.openRawResource(R.raw.config)
        properties.apply {
            load(assetManager)
        }

        return properties.getProperty("TREFEL_API_KEY")
    }
}