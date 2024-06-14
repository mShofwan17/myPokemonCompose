package me.project.network.utils

import android.util.Log
import me.project.shared.base.BaseResponse
import retrofit2.Response

suspend fun <T> Response<BaseResponse<T>>.validateBaseResponse(
    onSuccess: suspend (data: BaseResponse<T>) -> Unit
) {

    if (this.isSuccessful) {
        val body = this.body()
        body?.let { onSuccess(it) }
    } else {
        throw Exception(this.errorBody().toString())
    }
}

suspend fun <T> Response<T>.validateResponse(
    onSuccess: suspend (data: T) -> Unit
) {

    if (this.isSuccessful) {
        val body = this.body()
        body?.let { onSuccess(it) }
    } else {
        Log.i("TAG_getDetailPokemon", "validateResponse: ${this.errorBody().toString()}")
        throw Exception(this.errorBody().toString())
    }
}