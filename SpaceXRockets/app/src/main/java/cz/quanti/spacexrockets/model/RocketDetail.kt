package cz.quanti.spacexrockets.model

import com.google.gson.annotations.SerializedName
import kotlin.math.roundToInt

data class RocketDetail(
    val id: String,
    val name: String,
    val description: String,
    @SerializedName("first_flight")
    val firstFlight: String,
    val height: Size,
    val diameter: Size,
    val mass: Weight,
    @SerializedName("first_stage")
    val firstStage: Stage,
    @SerializedName("second_stage")
    val secondStage: Stage,
    @SerializedName("flickr_images")
    val images: List<String>
)

data class Size(
    val meters: Double,
    val feet: Double
) {
    // TODO potential to switch between metric and imperial
    override fun toString(): String {
        return "${meters.roundToInt()}m"
    }
}

data class Weight(
    val kg: Int,
    val lb: Int
) {
    // TODO potential to switch between metric and imperial
    override fun toString(): String {
        return "${kg/1000}t"
    }
}