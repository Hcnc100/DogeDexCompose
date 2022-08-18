package com.d34th.nullpointer.dogedex.data.local.room

import androidx.room.*
import com.d34th.nullpointer.dogedex.models.Dog
import kotlinx.coroutines.flow.Flow

@Dao
interface DogDAO {

    @Query("SELECT * FROM dogs ORDER BY `index` ASC")
    fun getAllDogs(): Flow<List<Dog>>

    @Query("DELETE FROM dogs")
    fun deleterAllDogs()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDogs(listDogs: List<Dog>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDog(dog: Dog)

    @Transaction
    fun updateAllDogs(listDogs: List<Dog>) {
        deleterAllDogs()
        insertDogs(listDogs)
    }

    @Query("SELECT * FROM dogs WHERE name is :name limit 1")
    fun getDogById(name: String): Dog?

}