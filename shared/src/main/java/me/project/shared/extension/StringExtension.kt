package me.project.shared.extension

import androidx.compose.ui.graphics.Color
import me.project.shared.ui.Bug
import me.project.shared.ui.Dark
import me.project.shared.ui.Dragon
import me.project.shared.ui.Electric
import me.project.shared.ui.Fairy
import me.project.shared.ui.Fighting
import me.project.shared.ui.Fire
import me.project.shared.ui.Flying
import me.project.shared.ui.Ghost
import me.project.shared.ui.Grass
import me.project.shared.ui.Ground
import me.project.shared.ui.Ice
import me.project.shared.ui.Normal
import me.project.shared.ui.Poison
import me.project.shared.ui.PrimaryColor
import me.project.shared.ui.Psychic
import me.project.shared.ui.Rock
import me.project.shared.ui.Steel
import me.project.shared.ui.Water

fun String.toImageUrl(): String {
    return "https://img.pokemondb.net/artwork/${this}.jpg"
}

fun String.getFullNickname(fibonacciCalculate: Long?): String {
    return if (fibonacciCalculate == null) this
    else "$this - $fibonacciCalculate"
}

fun String.toTypeColor(): Color {
    return when (this) {
        "grass" -> Grass
        "poison" -> Poison
        "fire" -> Fire
        "flying" -> Flying
        "bug" -> Bug
        "water" -> Water
        "normal" -> Normal
        "ground" -> Ground
        "fairy" -> Fairy
        "electric" -> Electric
        "fighting" -> Fighting
        "psychic" -> Psychic
        "rock" -> Rock
        "steel" -> Steel
        "ice" -> Ice
        "ghost" -> Ghost
        "dragon" -> Dragon
        "dark" -> Dark
        else -> PrimaryColor
    }
}