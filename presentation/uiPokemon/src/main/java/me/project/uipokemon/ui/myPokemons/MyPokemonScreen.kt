package me.project.uipokemon.ui.myPokemons

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import me.project.navigation.Screens
import me.project.uipokemon.components.ListGridContent

@Composable
fun MyPokemonScreen(
    navHostController: NavHostController,
    viewModel: MyPokemonViewModel = hiltViewModel()
) {
    val pokemons = viewModel.myPokemons.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.getMyPokemon()
    }

    Column {
        ListGridContent(
            itemsList = pokemons.value,
            onItemClick = {
                navHostController.navigate(Screens.DetailPokemon.passName(it.name))
            }
        )
    }
}