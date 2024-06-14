package me.project.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screens(val route: String, val label: String, val icon: ImageVector? = null) {
    data object Home : Screens(NavConstant.HOME, "Home", Icons.Default.PlayArrow)
    data object MyPokemon : Screens(NavConstant.MY_POKEMON, "My Pokemon", Icons.Default.Favorite)

    data object DetailPokemon : Screens("${NavConstant.DETAIL_POKEMON}/{name}", "Detail Pokemon") {
        fun passName(name: String?): String {
            return "${NavConstant.DETAIL_POKEMON}/$name"
        }
    }
}