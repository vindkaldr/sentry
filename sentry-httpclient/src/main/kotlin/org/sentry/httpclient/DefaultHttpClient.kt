package org.sentry.httpclient

import org.http4k.client.OkHttp
import org.http4k.core.HttpHandler
import org.http4k.core.Method
import org.http4k.core.Request
import org.sentry.httpclient.api.HttpClient

class DefaultHttpClient : HttpClient {
    override fun get(url: String): String {
        val client: HttpHandler = OkHttp()
        val response = client(Request(Method.GET, url))
        return response.bodyString()
    }
}
