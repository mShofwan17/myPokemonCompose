package me.project.network.services

import me.project.network.models.DetailPokemonDto
import me.project.shared.base.BaseResponse
import me.project.network.models.PokemonDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Response<BaseResponse<List<PokemonDto>>>

    @GET("pokemon/{name}")
    suspend fun getPokemonDetail(
        @Path("name") name:String
    ): Response<DetailPokemonDto?>
}