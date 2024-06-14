package me.project.domain.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import me.project.data.repositories.PokemonRepository
import javax.inject.Inject

class IsPokemonExistUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(name: String): Flow<Boolean> {
        return flow {
            val result = repository.isPokemonExist(name)
            emit(result != null)
        }.flowOn(Dispatchers.IO)
    }
}