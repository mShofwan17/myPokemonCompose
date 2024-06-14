package me.project.domain.usecases

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import me.project.data.repositories.PokemonRepository
import me.project.shared.base.BasePagingSource
import me.project.domain.utils.toUiPokemon
import javax.inject.Inject

class GetPokemonsUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
     operator fun invoke() = Pager(
        config = PagingConfig(
            pageSize = 10,
            enablePlaceholders = true
        ),

        pagingSourceFactory = {
            BasePagingSource(
                loadResult = { params ->
                    val nextPageNumber = params.key ?: 0
                    try {
                        val response = repository.getPokemon(
                            nextPageNumber,
                            params.loadSize
                        )

                        PagingSource.LoadResult.Page(
                            data = response.results.map { it.toUiPokemon() },
                            prevKey = if (response.previous != null) nextPageNumber - params.loadSize else null,
                            nextKey = if (response.next != null) nextPageNumber + params.loadSize else null
                        )
                    } catch (e: Exception) {
                        PagingSource.LoadResult.Error(e)
                    }
                }
            )
        }
    ).flow
}