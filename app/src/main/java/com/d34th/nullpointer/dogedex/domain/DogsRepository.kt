package com.d34th.nullpointer.dogedex.domain

import com.d34th.nullpointer.dogedex.models.Dog

interface DogsRepository {

    suspend fun getDogs(): List<Dog>
}
