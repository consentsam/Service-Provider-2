package com.laundry.user.utils

data class Resource<out T>(val status: Status, val data: T?, val errorValue: Throwable?) {

    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data,null)
        }

        fun <T> error(error: Throwable?, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, error)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }

    }

}