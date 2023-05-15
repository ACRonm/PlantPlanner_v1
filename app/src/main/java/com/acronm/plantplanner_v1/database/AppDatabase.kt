package com.acronm.plantplanner_v1.database

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Index
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase

@Database(entities = [Plants::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun plantsDao(): PlantsDao
}

@Entity(tableName = "plants", indices = [Index(value = ["name"], unique = true)])
data class Plants(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val description: String,
    var wateringInterval: Int,
    val sunlight: String,
    val image: String,
    val createdTimeStamp: Long,
    val modifiedTimestamp: Long

)

@Dao
interface PlantsDao {
     @Query("SELECT * FROM plants")
     fun getAll(): List<Plants>

     @Query("SELECT MAX(id) FROM plants")
     fun getMaxId(): Int

     @Query("SELECT * FROM plants WHERE id IN (:plantIds)")
     fun loadAllByIds(plantIds: IntArray): List<Plants>

     @Query("SELECT * FROM plants WHERE name LIKE :name LIMIT 1")
     fun findByName(name: String): Plants

     @Insert
     fun insertAll(vararg plants: Plants)

     @Delete
     fun delete(plants: Plants)
}