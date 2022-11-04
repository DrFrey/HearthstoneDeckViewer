package com.freyapps.hearthstonedeckviewer.data.repository

sealed class Result<out T>(val status: ResourceStatus, val data: T?, val message: String?) {

    data class Success<out R>(private val _data: R?) : Result<R>(
        status = ResourceStatus.SUCCESS,
        data = _data,
        message = null
    )

    data class Error(val exception: Throwable) : Result<Nothing>(
        status = ResourceStatus.ERROR,
        data = null,
        message = exception.message
    )

    object Loading : Result<Nothing>(
        status = ResourceStatus.LOADING,
        data = null,
        message = null
    )
}

inline fun <R, T> Result<T>.map(
    call: (T) -> (R)
): Result<R> = when (this) {
    is Result.Success -> {
        Result.Success(this.data?.let { call(it) })
    }
    is Result.Error -> {
        Result.Error(this.exception)
    }
    is Result.Loading -> {
        Result.Loading
    }
}

enum class ResourceStatus {
    SUCCESS,
    ERROR,
    LOADING
}
