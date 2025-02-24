package ru.kpfu.itis.paramonov.network.external.exception

data class HttpException(val code: Int): Throwable()