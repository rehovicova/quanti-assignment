package cz.quanti.spacexrockets.api

import cz.quanti.spacexrockets.model.Rocket
import cz.quanti.spacexrockets.model.RocketDetail
import javax.inject.Inject

class RocketRepo @Inject constructor(
    private val api: Endpoints
)  {

    suspend fun getRockets(): List<Rocket> {
        return api.getRockets()
    }

    suspend fun getRocket(id: String): RocketDetail {
        return api.getRocket(id)
    }
}