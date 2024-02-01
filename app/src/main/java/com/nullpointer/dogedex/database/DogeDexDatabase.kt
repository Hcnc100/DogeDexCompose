package com.nullpointer.dogedex.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nullpointer.dogedex.data.dogs.local.DogDAO
import com.nullpointer.dogedex.models.dogs.entity.DogEntity

@Database(
    entities = [DogEntity::class],
    version = 2,
    exportSchema = false
)
abstract class DogeDexDatabase : RoomDatabase() {

    abstract fun getDogDao(): DogDAO
}