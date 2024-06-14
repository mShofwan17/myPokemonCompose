package me.project.data.repositories

import me.project.data.dataSource.PokemonDataSource
import me.project.data.models.DetailPokemon
import me.project.data.models.MyPokemon
import me.project.data.models.Pokemon
import me.project.shared.base.BaseResponse
import javax.inject.Inject

class PokemonRepositoryImpl @Inject constructor(
    private val dataSource: PokemonDataSource
) : PokemonRepository {
    override suspend fun getPokemon(
        offset: Int,
        limit: Int
    ): BaseResponse<List<Pokemon>> {
        return dataSource.getPokemon(offset, limit)
    }

    override suspend fun getDetailPokemon(name: String): DetailPokemon? {
        return dataSource.getDetailPokemon(name)
    }

    override suspend fun catchPokemon(myPokemon: MyPokemon): Boolean {
        return dataSource.catchPokemon(myPokemon)
    }

    override suspend fun getMyPokemon(): List<MyPokemon> {
        return dataSource.getMyPokemon()
    }

    override suspend fun renamePokemon(myPokemon: MyPokemon): String {
        return dataSource.renameNicknamePokemon(myPokemon)
    }

    override suspend fun releasePokemon(name: String): Boolean {
        return dataSource.releasePokemon(name)
    }

    override suspend fun isPokemonExist(name: String): MyPokemon? {
        return dataSource.isPokemonExist(name)
    }

}