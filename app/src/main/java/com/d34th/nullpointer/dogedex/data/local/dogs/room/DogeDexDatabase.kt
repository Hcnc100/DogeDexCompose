package com.d34th.nullpointer.dogedex.data.local.dogs.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.d34th.nullpointer.dogedex.models.Dog

@Database(
    entities = [Dog::class],
    version = 1,
    exportSchema = false
)
abstract class DogeDexDatabase : RoomDatabase() {

    abstract fun getDogDao(): DogDAO
}