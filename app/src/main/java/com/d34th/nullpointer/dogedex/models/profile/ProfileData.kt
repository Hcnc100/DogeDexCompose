package com.d34th.nullpointer.dogedex.models.profile

data class ProfileData(
    val email: String,
    val dogsCaught: Int,
) {
    companion object {
        val preview: ProfileData = ProfileData(
            email = "email@example.com",
            dogsCaught = 0
        )
    }
}
