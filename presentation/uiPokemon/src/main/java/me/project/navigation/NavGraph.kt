package me.project.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import me.project.uipokemon.ui.detailPokemon.DetailPokemonScreen
import me.project.uipokemon.ui.homePokemons.HomeScreen
import me.project.uipokemon.ui.myPokemons.MyPokemonScreen

@Composable
fun SetupNavGraph(navHostController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        navController = navHostController,
        startDestination = Screens.Home.route,
        modifier = modifier
    ) {
        composable(route = Screens.Home.route) {
            HomeScreen(navHostController = navHostController)
        }
        composable(route = Screens.MyPokemon.route) {
            MyPokemonScreen(navHostController = navHostController)
        }
        composable(
            route = Screens.DetailPokemon.route,
            arguments = listOf(
                navArgument(name = NavConstant.DETAIL_POKEMON_NAME) { type = NavType.StringType }
            )
        ) {
            DetailPokemonScreen(navHostController = navHostController)
        }
    }
}