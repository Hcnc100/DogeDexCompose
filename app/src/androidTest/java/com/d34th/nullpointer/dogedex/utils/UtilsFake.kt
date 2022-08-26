package com.d34th.nullpointer.dogedex.utils

import com.d34th.nullpointer.dogedex.models.Dog

object UtilsFake {
    fun generateListsDogs(numberDogsFake: Int, numberHasDog: Int = 0): List<Dog> {
        return when {
            numberDogsFake <= 0 -> throw Exception("The number of dogs generated must be greater than zero")
            numberHasDog < 0 -> throw Exception("The number of dog that has,  must be greater than zero")
            numberHasDog > numberDogsFake -> throw Exception("The number of dogs generated must be greater than the number of dog that has")
            numberHasDog == 0 -> (0 until numberDogsFake).map {
                Dog(index = it.toLong())
            }
            numberDogsFake == numberHasDog -> (0 until numberDogsFake).map {
                Dog(index = it.toLong(), hasDog = true, name = "dog-$it")
            }
            else -> {
                val listFakeDogs =
                    (0 until numberDogsFake).map {
                        Dog(index = it.toLong())
                    }
                        .toMutableList()
                val listRandom = (0..numberDogsFake).asSequence()
                    .shuffled()
                    .take(numberHasDog)
                    .toList()
                listRandom.forEach { index ->
                    listFakeDogs[index] =
                        listFakeDogs[index].copy(hasDog = true, name = "dog-$index")
                }
                listFakeDogs
            }
        }

    }

}