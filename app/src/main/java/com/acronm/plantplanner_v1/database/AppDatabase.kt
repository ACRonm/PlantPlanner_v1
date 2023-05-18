package com.acronm.plantplanner_v1.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Index
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase

 @Database(entities = [AppDatabase.Plant::class], version = 1)
    abstract class AppDatabase : RoomDatabase() {
     abstract fun plantsDao(): PlantsDao

     companion object {
         private var INSTANCE: AppDatabase? = null

         fun getInstance(context: Context): AppDatabase {
             if (INSTANCE == null) {
                 synchronized(AppDatabase::class) {
                     INSTANCE = Room.databaseBuilder(
                         context.applicationContext,
                         AppDatabase::class.java,
                         "plants.db"
                     ).build()
                 }
             }
             return INSTANCE!!
         }
     }

     @Entity(tableName = "plants", indices = [Index(value = ["name"], unique = true)])
     data class Plant(
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
         fun getAll(): List<Plant>

         @Query("SELECT * FROM plants")
            fun getAllLiveData(): LiveData<List<Plant>>

         @Query("SELECT MAX(id) FROM plants")
         fun getMaxId(): Int

         @Query("SELECT * FROM plants WHERE id IN (:plantIds)")
         fun loadAllByIds(plantIds: IntArray): List<Plant>

         @Query("SELECT * FROM plants WHERE name LIKE :name LIMIT 1")
         fun findByName(name: String): Plant

         @Insert
         fun insertAll(vararg plants: Plant)

         @Delete
         fun delete(plant: Plant)

         @Insert
         fun insert(plant: Plant)

         @Delete
         fun deleteAll(vararg plants: Plant)
     }
 }