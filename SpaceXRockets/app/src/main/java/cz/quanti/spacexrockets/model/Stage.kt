package cz.quanti.spacexrockets.model

import com.google.gson.annotations.SerializedName

data class Stage(
    val reusable: Boolean,
    val engines: Int,
    @SerializedName("fuel_amount_tons")
    val fuelAmount: Double,
    @SerializedName("burn_time_sec")
    val burnTime: Int?
)