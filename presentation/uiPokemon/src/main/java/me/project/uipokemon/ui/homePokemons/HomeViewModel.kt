package me.project.uipokemon.ui.homePokemons

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import me.project.domain.usecases.GetPokemonsUseCase
import me.project.shared.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getPokemonsUseCase: GetPokemonsUseCase
) : BaseViewModel() {

    val getPokemons =
        getPokemonsUseCase().cachedIn(viewModelScope)
}