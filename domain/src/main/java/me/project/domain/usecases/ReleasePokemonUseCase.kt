package me.project.domain.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import me.project.data.repositories.PokemonRepository
import me.project.shared.extension.isPrimeNumber
import javax.inject.Inject
import kotlin.random.Random

class ReleasePokemonUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(name: String): Flow<Boolean> {
        return flow {
            val isPrimeNumber = Random.nextInt().isPrimeNumber()
            if (isPrimeNumber) {
                val result = repository.releasePokemon(name)
                emit(result)
            } else emit(false)
        }.flowOn(Dispatchers.IO)
    }
}