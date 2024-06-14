package me.project.local

import androidx.room.Database
import androidx.room.RoomDatabase
import me.project.local.dao.MyPokemonDao
import me.project.local.entity.MyPokemonEntity

@Database(
    entities = [
        MyPokemonEntity::class,
    ], version = 1, exportSchema = false
)
abstract class PokemonDatabase : RoomDatabase() {

    abstract fun pokemonDao(): MyPokemonDao
}