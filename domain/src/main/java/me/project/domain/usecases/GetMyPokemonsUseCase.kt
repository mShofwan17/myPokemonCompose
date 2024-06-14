package me.project.domain.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import me.project.data.repositories.PokemonRepository
import me.project.domain.models.UiPokemon
import me.project.domain.utils.toUiPokemon
import javax.inject.Inject

class GetMyPokemonsUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(): Flow<List<UiPokemon>> {
        return flow {
            val result = repository.getMyPokemon()
                .map { it.toUiPokemon() }
            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}