package com.freyapps.hearthstonedeckviewer.common

import android.util.Log
import retrofit2.HttpException
import retrofit2.Response
import com.freyapps.hearthstonedeckviewer.data.repository.Result

inline fun <T> request(
    call: () -> Response<T>
): Result<T> {
    return try {
        val response = call.invoke()
        when (response.isSuccessful) {
            true -> Result.Success(response.body())
            false -> Result.Error(HttpException(response))
                .also { Log.w("NetworkUtils", "Unsuccessful response: $response") }
        }
    } catch (exception: Throwable) {
        Log.e("NetworkUtils", exception.toString())
        Result.Error(exception)
    }
}
