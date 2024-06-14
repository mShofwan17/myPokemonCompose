package me.project.shared.base

data class BaseResponse<T>(
    val count: Int = 0,
    val next: String? = null,
    val previous: String? = null,
    val results: T
)
