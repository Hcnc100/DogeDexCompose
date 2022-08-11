package com.d34th.nullpointer.dogedex.domain

import com.d34th.nullpointer.dogedex.core.states.Resource
import com.d34th.nullpointer.dogedex.models.ApiResponse
import com.d34th.nullpointer.dogedex.models.Dog

interface DogsRepository {
    suspend fun getDogs(): ApiResponse<List<Dog>>
}
