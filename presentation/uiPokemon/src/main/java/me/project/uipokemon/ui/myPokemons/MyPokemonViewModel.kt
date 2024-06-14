package me.project.uipokemon.ui.myPokemons

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import me.project.domain.models.UiPokemon
import me.project.domain.usecases.GetMyPokemonsUseCase
import javax.inject.Inject

@HiltViewModel
class MyPokemonViewModel @Inject constructor(
    private val getMyPokemonsUseCase: GetMyPokemonsUseCase
) : ViewModel() {
    private val _myPokemons = MutableStateFlow(listOf<UiPokemon>())
    val myPokemons get() = _myPokemons

    fun getMyPokemon() {
        viewModelScope.launch {
            getMyPokemonsUseCase().collectLatest {
                _myPokemons.emit(it)
            }
        }
    }
}