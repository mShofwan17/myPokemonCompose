package me.project.data.utils

import me.project.data.models.DetailPokemon
import me.project.data.models.MyPokemon
import me.project.data.models.Pokemon
import me.project.data.models.Stats
import me.project.local.entity.MyPokemonEntity
import me.project.network.models.DetailPokemonDto
import me.project.shared.base.BaseResponse
import me.project.network.models.PokemonDto
import me.project.network.utils.validateBaseResponse
import retrofit2.Response

fun PokemonDto.toPokemon(): Pokemon {
    return Pokemon(
        name = this.name,
        imgUrl = this.url
    )
}

fun MyPokemonEntity.toMyPokemon(): MyPokemon{
    return MyPokemon(
        name = name,
        imgUrl = imageUrl,
        nickName = nickName,
        renameCount = renameCount,
        fibonacciCalculate = fibonacciCalculate
    )
}

suspend fun Response<BaseResponse<List<PokemonDto>>>.toResponsePokemon(): BaseResponse<List<Pokemon>> {
    var baseResponse: BaseResponse<List<Pokemon>> = BaseResponse(results = emptyList())
    this.validateBaseResponse {
        baseResponse = BaseResponse(
            results = it.results.map { it.toPokemon() },
            count = it.count,
            previous = it.previous,
            next = it.next
        )
    }

    return baseResponse
}

fun DetailPokemonDto.toDetailPokemon() : DetailPokemon {
    return DetailPokemon(
        weight = weight,
        height = height,
        stats = stats.map { statsDto -> Stats(name = statsDto.stat.name, stat = statsDto.base_stat) },
        abilities = abilities.map { it.ability.name },
        types = types.map { it.type.name },
        id = id
    )
}