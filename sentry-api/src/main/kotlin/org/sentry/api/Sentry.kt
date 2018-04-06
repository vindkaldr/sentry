package org.sentry.api

interface Sentry {
    suspend fun preview(url: String): String
    suspend fun add(url: String)
    suspend fun check(url: String): CheckResult
}
