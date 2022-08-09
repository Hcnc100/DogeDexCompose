package com.d34th.nullpointer.dogedex.data.remote.dogs

import com.d34th.nullpointer.dogedex.models.Dog

interface DogsDataSource {

    suspend fun getDogs():List<Dog>
}