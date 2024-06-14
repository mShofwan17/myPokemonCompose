package me.project.domain.usecases

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import me.project.data.models.MyPokemon
import me.project.data.repositories.PokemonRepository
import me.project.shared.extension.toImageUrl
import javax.inject.Inject
import kotlin.random.Random

class CatchPokemonUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(name: String): Flow<Boolean> {
        return flow {
            val isCatched = Random.nextBoolean()
            if (isCatched) {
                val result = repository.catchPokemon(
                    MyPokemon(
                        name = name,
                        imgUrl = name.toImageUrl(),
                        nickName = name,
                        renameCount = null
                    )
                )

                emit(result)
            } else emit(false)


        }.flowOn(Dispatchers.IO)
    }
}