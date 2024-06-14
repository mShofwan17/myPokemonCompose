package me.project.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import me.project.local.entity.MyPokemonEntity

@Dao
interface MyPokemonDao {
    @Query("SELECT * FROM tb_my_pokemon")
    suspend fun getAll(): List<MyPokemonEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(item: MyPokemonEntity): Long

    @Query("SELECT * FROM tb_my_pokemon WHERE name=:name")
    suspend fun findPokemon(name: String): MyPokemonEntity?

    @Update
    suspend fun update(item: MyPokemonEntity): Int

    @Delete
    suspend fun delete(items: MyPokemonEntity): Int

    @Query("DELETE FROM tb_my_pokemon")
    suspend fun deleteAll(): Int
}