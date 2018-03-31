package org.sentry.httpclient.api

interface HttpClient {
    fun get(url: String): String
}
