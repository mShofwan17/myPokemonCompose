package me.project.data.models

data class DetailPokemon(
    val id: Int,
    val weight: Int,
    val height: Int,
    val stats: List<Stats>,
    val abilities: List<String>,
    val types: List<String>
)
