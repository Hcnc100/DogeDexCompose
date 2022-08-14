package com.d34th.nullpointer.dogedex.data.local.room

import androidx.room.*
import com.d34th.nullpointer.dogedex.models.Dog
import kotlinx.coroutines.flow.Flow

@Dao
interface DogDAO {

    @Query("select * from dogs")
    fun getAllDogs(): Flow<List<Dog>>

    @Query("delete from dogs")
    fun deleterAllDogs()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertDogs(listDogs: List<Dog>)

    @Transaction
    fun updateAllDogs(listDogs: List<Dog>) {
        deleterAllDogs()
        insertDogs(listDogs)
    }

}