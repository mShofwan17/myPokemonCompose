package me.project.data.dataSource

import android.util.Log
import me.project.data.models.DetailPokemon
import me.project.data.models.MyPokemon
import me.project.data.models.Pokemon
import me.project.data.utils.toDetailPokemon
import me.project.data.utils.toMyPokemon
import me.project.data.utils.toResponsePokemon
import me.project.local.PokemonDatabase
import me.project.network.services.PokemonService
import me.project.network.utils.validateResponse
import me.project.shared.base.BaseResponse
import me.project.shared.extension.getFullNickname
import javax.inject.Inject

class PokemonDataSource @Inject constructor(
    private val service: PokemonService,
    private val database: PokemonDatabase
) {
    suspend fun getPokemon(offset: Int, limit: Int): BaseResponse<List<Pokemon>> {
        return service.getPokemonList(
            offset = offset,
            limit = limit
        ).toResponsePokemon()
    }

    suspend fun getMyPokemon(): List<MyPokemon> {
        return try {
            val items = database.pokemonDao().getAll()
            items.map { it.toMyPokemon() }
        } catch (e: Exception) {
            throw Exception(e)
        }
    }

    suspend fun getDetailPokemon(name: String): DetailPokemon? {
        var detailPokemon: DetailPokemon? = null
        service.getPokemonDetail(name).validateResponse {
            detailPokemon = it?.toDetailPokemon()
        }
        return detailPokemon
    }

    suspend fun catchPokemon(myPokemon: MyPokemon): Boolean {
        return try {
            val catchPokemon = database.pokemonDao().add(myPokemon.toPokemonEntity())
            catchPokemon != -1L
        } catch (e: Exception) {
            throw Exception(e)
        }
    }

    suspend fun renameNicknamePokemon(myPokemon: MyPokemon): String {
        return try {
            val getEntity = database.pokemonDao().findPokemon(name = myPokemon.name)
                ?.copy(
                    nickName = myPokemon.nickName,
                    renameCount = myPokemon.renameCount,
                    fibonacciCalculate = myPokemon.fibonacciCalculate
                )
            getEntity?.let { database.pokemonDao().update(getEntity) }
            val getPokemon =
                database.pokemonDao().findPokemon(name = myPokemon.name)?.toMyPokemon()
            "${getPokemon?.nickName?.getFullNickname(getPokemon.fibonacciCalculate)}"
        } catch (e: Exception) {
            throw Exception(e)
        }
    }

    suspend fun releasePokemon(name: String): Boolean {
        return try {
            val findPoke = database.pokemonDao().findPokemon(name)
            val release = findPoke?.let { database.pokemonDao().delete(it) }
            release != -1
        } catch (e: Exception) {
            throw Exception(e)
        }
    }

    suspend fun isPokemonExist(name: String): MyPokemon? {
        return try {
            val findPoke = database.pokemonDao().findPokemon(name)
            findPoke?.toMyPokemon()
        } catch (e: Exception) {
            throw Exception(e)
        }
    }
}