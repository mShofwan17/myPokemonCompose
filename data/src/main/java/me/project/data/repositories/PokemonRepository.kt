package me.project.data.repositories

import me.project.data.models.DetailPokemon
import me.project.data.models.MyPokemon
import me.project.data.models.Pokemon
import me.project.shared.base.BaseResponse

interface PokemonRepository {
    suspend fun getPokemon(offset: Int, limit: Int): BaseResponse<List<Pokemon>>
    suspend fun getDetailPokemon(name: String): DetailPokemon?
    suspend fun catchPokemon(myPokemon: MyPokemon): Boolean
    suspend fun getMyPokemon(): List<MyPokemon>
    suspend fun renamePokemon(myPokemon: MyPokemon): String
    suspend fun releasePokemon(name: String): Boolean
    suspend fun isPokemonExist(name: String): MyPokemon?
}