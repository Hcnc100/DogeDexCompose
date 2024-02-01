package com.nullpointer.dogedex.core.utils

import com.nullpointer.dogedex.models.dogs.data.DogData

object UtilsFake {
    fun generateListsDogs(numberDogsFake: Int, numberHasDog: Int = 0): List<DogData> {
        return emptyList()
//        return when {
//            numberDogsFake <= 0 -> throw Exception("The number of dogs generated must be greater than zero")
//            numberHasDog < 0 -> throw Exception("The number of dog that has,  must be greater than zero")
//            numberHasDog > numberDogsFake -> throw Exception("The number of dogs generated must be greater than the number of dog that has")
//            numberHasDog == 0 -> (0 until numberDogsFake).map {
//                DogData(id = it.toLong())
//            }
//            numberDogsFake == numberHasDog -> (0 until numberDogsFake).map {
//                DogData(index = it.toLong(), hasDog = true, name = "dog-$it")
//            }
//            else -> {
//                val listFakeDogData =
//                    (0 until numberDogsFake).map {
//                        DogData(index = it.toLong())
//                    }
//                        .toMutableList()
//                val listRandom = (0..numberDogsFake).asSequence()
//                    .shuffled()
//                    .take(numberHasDog)
//                    .toList()
//                listRandom.forEach { index ->
//                    listFakeDogData[index] =
//                        listFakeDogData[index].copy(hasDog = true, name = "dog-$index")
//                }
//                listFakeDogData
//            }
//        }

    }

}