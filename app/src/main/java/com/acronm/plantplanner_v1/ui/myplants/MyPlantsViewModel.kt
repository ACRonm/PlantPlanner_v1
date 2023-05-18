package com.acronm.plantplanner_v1.ui.myplants

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.acronm.plantplanner_v1.database.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyPlantsViewModel(application: Application) : AndroidViewModel(application) {

    private val _plantsLiveData = MutableLiveData<List<AppDatabase.Plant>?>(null)
    val plantsLiveData: LiveData<List<AppDatabase.Plant>?>
        get() = _plantsLiveData

    private var database: AppDatabase
    private val tag = "MyPlantsViewModel"


    init {
        val context = application.applicationContext
        database = AppDatabase.getInstance(context)
        fetchAllPlants()
    }

    private fun fetchAllPlants(): LiveData<List<AppDatabase.Plant>?> {
        viewModelScope.launch(Dispatchers.IO) {
            val plants = database.plantsDao().getAll()
            withContext(Dispatchers.Main) {
                _plantsLiveData.value = plants
            }
        }
        return plantsLiveData
    }

    fun insertPlant(plant: AppDatabase.Plant) {
        viewModelScope.launch(Dispatchers.IO) {
            database.plantsDao().insert(plant)

            Log.d(tag, "insertPlant: $plant")
            // Fetch the plants again after inserting a plant
            fetchAllPlants()
        }
    }

        fun deletePlant(plant: AppDatabase.Plant) {
            viewModelScope.launch(Dispatchers.IO) {
                database.plantsDao().delete(plant)
                Log.d(tag, "deletePlant: $plant")

                // Fetch the plants again after inserting a plant
                fetchAllPlants()
            }
        }
    }
