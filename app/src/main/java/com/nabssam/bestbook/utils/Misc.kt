package com.nabssam.bestbook.utils

class ValidationException(message: String) : Exception(message)

sealed class Result<out T> {
    data class Success<out T>(val value: T) : Result<T>()
    data class Error(val message: String, val cause: Exception? = null) : Result<Nothing>()
}

