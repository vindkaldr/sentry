package org.sentry.core.hash.api

interface HashService {
    fun getHash(string: String): String
}
