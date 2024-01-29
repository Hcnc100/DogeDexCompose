package com.d34th.nullpointer.dogedex.data.dogs.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.d34th.nullpointer.dogedex.models.dogs.entity.DogEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DogDAO {

    @Query("SELECT * FROM dogs ORDER BY id ASC")
    fun getAllDogs(): Flow<List<DogEntity>>

    @Query("SELECT * FROM dogs WHERE hasDog")
    fun getAllHasDog(): List<DogEntity>

    @Query("DELETE FROM dogs")
    fun deleterAllDogs()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllDogs(listDogData: List<DogEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDog(dogData: DogEntity)

    @Transaction
    fun updateAllDogs(listDogData: List<DogEntity>) {
        deleterAllDogs()
        insertAllDogs(listDogData)
    }

    @Query("SELECT * FROM dogs WHERE name is :name limit 1")
    fun getDogByName(name: String): DogEntity?

    @Query("SELECT * FROM dogs WHERE id is :id limit 1")
    fun getDogById(id: Long): DogEntity?

}