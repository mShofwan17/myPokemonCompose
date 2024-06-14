package me.project.domain.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import me.project.data.repositories.PokemonRepository
import me.project.domain.models.UiDetailPokemon
import me.project.domain.utils.toUiDetailPokemon
import me.project.shared.extension.getFullNickname
import javax.inject.Inject

class GetDetailPokemonUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(name: String): Flow<UiDetailPokemon?> {
        return flow {
            val result = repository.getDetailPokemon(name)
            result?.let {
                val local = repository.isPokemonExist(name)
                emit(
                    it.toUiDetailPokemon(
                        name,
                        nickName = local?.nickName?.getFullNickname(local.fibonacciCalculate)
                    )
                )
            }

        }.flowOn(Dispatchers.IO)
    }
}