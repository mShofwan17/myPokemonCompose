package me.project.data.models

import me.project.local.entity.MyPokemonEntity

data class MyPokemon(
    val name: String,
    val imgUrl: String,
    val nickName: String,
    val renameCount: Long?,
    val fibonacciCalculate: Long? = null
) {
    fun toPokemonEntity(): MyPokemonEntity {
        return MyPokemonEntity(
            id = 0L,
            name = name,
            imageUrl = imgUrl,
            nickName = nickName,
            renameCount = renameCount
        )
    }
}
