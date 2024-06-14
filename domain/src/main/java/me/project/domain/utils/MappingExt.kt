package me.project.domain.utils

import me.project.data.models.DetailPokemon
import me.project.data.models.MyPokemon
import me.project.data.models.Pokemon
import me.project.domain.models.UiDetailPokemon
import me.project.domain.models.UiPokemon
import me.project.domain.models.UiStats
import me.project.shared.extension.getFullNickname
import me.project.shared.extension.idToImageUrl
import me.project.shared.extension.toImageUrl

fun Pokemon.toUiPokemon(): UiPokemon {
    return UiPokemon(
        name = name,
        url = imgUrl,
        imgUrl = name.toImageUrl()
    )
}

fun MyPokemon.toUiPokemon(): UiPokemon {
    return UiPokemon(
        name = name,
        nickName = nickName.getFullNickname(fibonacciCalculate),
        url = imgUrl,
        imgUrl = imgUrl
    )
}

fun DetailPokemon.toUiDetailPokemon(
    name: String,
    nickName: String?,
): UiDetailPokemon {
    return UiDetailPokemon(
        name = name,
        nickName = nickName,
        imgUrl = id.idToImageUrl(),
        weight = weight,
        height = height,
        stats = stats.map { UiStats(it.name, it.stat) },
        abilities = abilities,
        types = types
    )
}