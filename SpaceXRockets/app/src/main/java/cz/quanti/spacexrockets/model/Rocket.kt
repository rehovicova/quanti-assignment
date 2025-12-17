package cz.quanti.spacexrockets.model

import com.google.gson.annotations.SerializedName


data class Rocket(
    val id: String,
    val name: String,
    @SerializedName("first_flight")
    val firstFlight: String
)