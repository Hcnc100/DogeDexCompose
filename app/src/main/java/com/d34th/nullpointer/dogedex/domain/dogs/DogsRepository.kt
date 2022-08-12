package com.d34th.nullpointer.dogedex.domain.dogs

import com.d34th.nullpointer.dogedex.models.Dog
import kotlinx.coroutines.flow.Flow

interface DogsRepository {
    suspend fun getDogs(): Flow<List<Dog>>
}
