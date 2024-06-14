package me.project.uipokemon.ui.detailPokemon

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import me.project.domain.models.UiDetailPokemon
import me.project.domain.usecases.CatchPokemonUseCase
import me.project.domain.usecases.GetDetailPokemonUseCase
import me.project.domain.usecases.IsPokemonExistUseCase
import me.project.domain.usecases.ReleasePokemonUseCase
import me.project.domain.usecases.RenamePokemonUseCase
import me.project.navigation.NavConstant
import me.project.shared.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class DetailPokemonViewModel @Inject constructor(
    private val getDetailPokemonUseCase: GetDetailPokemonUseCase,
    private val catchPokemonUseCase: CatchPokemonUseCase,
    private val releasePokemonUseCase: ReleasePokemonUseCase,
    private val renamePokemonUseCase: RenamePokemonUseCase,
    private val isPokemonExistUseCase: IsPokemonExistUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel() {
    private val _isMyPokemon = MutableStateFlow(false)
    val isMyPokemon get() = _isMyPokemon.asStateFlow()

    private val _detailPokemon = MutableStateFlow<UiDetailPokemon?>(null)
    val detailPokemon get() = _detailPokemon.asStateFlow()

    private val _catch = MutableStateFlow<Boolean?>(null)
    val catch get() = _catch.asStateFlow()


    private val _release = MutableStateFlow<Boolean?>(null)
    val release get() = _release.asStateFlow()

    init {
        val name: String? = savedStateHandle[NavConstant.DETAIL_POKEMON_NAME]
        name?.let {
            isPokemonExistInDb(it)
            getDetailPokemon(it)
        }
    }

    private fun isPokemonExistInDb(name: String) {
        viewModelScope.launch {
            isPokemonExistUseCase(name = name).collectLatest {
                _isMyPokemon.emit(it)
            }
        }
    }

    private fun getDetailPokemon(name: String) {
        viewModelScope.launch {
            showLoading()
            delay(1000)
            getDetailPokemonUseCase(name)
                .catch {
                    resetBaseState()
                    errorMessage(it)
                }
                .collectLatest {
                    resetBaseState()
                    _detailPokemon.emit(it)
                }
        }
    }

    fun catchPokemon(name: String) {
        viewModelScope.launch {
            catchPokemonUseCase(name).collectLatest {
                delay(1000)
                _catch.emit(it)
                delay(300)
                _isMyPokemon.emit(it)
            }
        }
    }

    fun releasePokemon(name: String) {
        viewModelScope.launch {
            delay(1000)
            releasePokemonUseCase(name)
                .catch {
                    errorMessage(it)
                }
                .collectLatest {
                    _release.emit(it)
                    _isMyPokemon.emit(!it)
                    if (it) _detailPokemon.update { detail -> detail?.copy(nickName = null) }
                }
        }
    }

    fun renamePokemon(name: String, nickname: String) {
        viewModelScope.launch {
            showLoading()
            delay(800)
            renamePokemonUseCase(name, nickname).collectLatest { nickname ->
                resetBaseState()
                _detailPokemon.update {
                    it?.copy(nickName = nickname)
                }
            }
        }
    }

    fun showDialog() {
        viewModelScope.launch {
            _catch.emit(true)
        }
    }

    fun resetState() {
        viewModelScope.launch {
            _catch.emit(null)
            _release.emit(null)
            resetBaseState()
        }
    }
}