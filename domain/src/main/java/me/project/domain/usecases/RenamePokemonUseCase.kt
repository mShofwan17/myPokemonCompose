package me.project.domain.usecases

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import me.project.data.models.MyPokemon
import me.project.data.repositories.PokemonRepository
import me.project.shared.extension.counter
import me.project.shared.extension.fibonacciRename
import me.project.shared.extension.toImageUrl
import javax.inject.Inject

class RenamePokemonUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(
        name: String,
        nickname: String
    ): Flow<String> {
        return flow {
            val getRenameCount = repository.isPokemonExist(name)?.renameCount
            val result = repository.renamePokemon(
                MyPokemon(
                    name = name,
                    imgUrl = name.toImageUrl(),
                    nickName = nickname,
                    renameCount = getRenameCount.counter(),
                    fibonacciCalculate = getRenameCount?.toInt()?.fibonacciRename()
                )
            )

            emit(result)
        }.flowOn(Dispatchers.IO)
    }
}