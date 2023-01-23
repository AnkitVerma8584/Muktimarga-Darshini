package com.ass.muktimargadarshini.domain.utils

sealed class Resource<out R> {
    object Loading : Resource<Nothing>()
    data class Cached<out R>(val result: R) : Resource<R>()
    data class Success<out R>(val result: R) : Resource<R>()
    data class Failure(val error: StringUtil) : Resource<Nothing>()
}