package com.acronm.plantplanner_v1.model

data class Plant(
    val id: Int,
    val name: String,
    val description: String,
    var wateringInterval: Int,
    val sunlight: String,
    val image: String,
    val createdTimeStamp: Long,
    val modifiedTimestamp: Long
)