package me.project.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.project.data.dataSource.PokemonDataSource
import me.project.data.repositories.PokemonRepository
import me.project.data.repositories.PokemonRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun providePokemonRepository(dataSource: PokemonDataSource): PokemonRepository {
        return PokemonRepositoryImpl(dataSource)
    }
}