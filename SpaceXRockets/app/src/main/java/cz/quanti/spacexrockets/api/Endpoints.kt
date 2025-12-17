package cz.quanti.spacexrockets.api

import cz.quanti.spacexrockets.model.Rocket
import cz.quanti.spacexrockets.model.RocketDetail
import retrofit2.http.GET
import retrofit2.http.Path

interface Endpoints {

    @GET("rockets")
    suspend fun getRockets(): List<Rocket>

    @GET("rockets/{id}")
    suspend fun getRocket(@Path("id") id: String): RocketDetail

}