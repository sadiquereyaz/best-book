package com.nabssam.bestbook.utils

sealed class Resource<T>(
    data: T? = null,
    message: String? = null
) {
    class Loading<T> : Resource<T>()
    class Success<T>(val data: T?, val message: String? = null) : Resource<T>(data = data, message = message)
    class Error<T>(val message: String?) : Resource<T>(message = message)
}

// TODO: https://youtu.be/MiLN2vs2Oe0