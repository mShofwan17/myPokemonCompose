package me.project.domain.models

data class UiDetailPokemon(
    val name: String,
    val nickName: String? = null,
    val imgUrl: String,
    val weight: Int,
    val height: Int,
    val stats: List<UiStats>,
    val abilities: List<String>,
    val types: List<String>
)
