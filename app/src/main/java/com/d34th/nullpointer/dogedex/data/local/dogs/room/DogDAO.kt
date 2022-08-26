package com.d34th.nullpointer.dogedex.data.local.dogs.room

import androidx.room.*
import com.d34th.nullpointer.dogedex.models.Dog
import kotlinx.coroutines.flow.Flow

@Dao
interface DogDAO {

    @Query("SELECT * FROM dogs ORDER BY `index` ASC")
    fun getAllDogs(): Flow<List<Dog>>

    @Query("SELECT * FROM dogs WHERE hasDog")
    fun getAllHasDog(): List<Dog>

    @Query("DELETE FROM dogs")
    fun deleterAllDogs()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllDogs(listDogs: List<Dog>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDog(dog: Dog)

    @Transaction
    fun updateAllDogs(listDogs: List<Dog>) {
        deleterAllDogs()
        insertAllDogs(listDogs)
    }

    @Query("SELECT * FROM dogs WHERE name is :name limit 1")
    fun getDogByName(name: String): Dog?

}