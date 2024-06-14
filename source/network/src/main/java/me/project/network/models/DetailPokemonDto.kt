package me.project.network.models

data class DetailPokemonDto(
    val abilities: List<AbilitiesDto>,
    val weight: Int,
    val height: Int,
    val stats: List<StatsDto>,
    val types: List<TypesDto>,
    val id: Int
)
