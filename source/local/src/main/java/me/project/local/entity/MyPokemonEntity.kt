package me.project.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_my_pokemon")
data class MyPokemonEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val nickName: String,
    val imageUrl: String,
    val renameCount: Long? = null,
    val fibonacciCalculate: Long? = null
)
