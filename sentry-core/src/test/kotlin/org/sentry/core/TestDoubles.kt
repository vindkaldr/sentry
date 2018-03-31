package org.sentry.core

import org.sentry.core.hash.api.HashService
import org.sentry.httpclient.api.HttpClient
import org.sentry.storage.api.KeyValueStorage

fun dummyKeyValueStorage(): KeyValueStorage = DummyStorage
fun dummyHttpClient(): HttpClient = DummyHttpClient
fun dummyHashService(): HashService = DummyHashService

fun httpClientStub(vararg urlsWithResources: Pair<String, List<String>>): HttpClient {
    return HttpClientStub(urlsWithResources.toMap())
}

fun hashServiceStub(vararg urlsWithResources: Pair<String, String>): HashService {
    return HashServicesStub(urlsWithResources.toMap())
}

private object DummyStorage : KeyValueStorage {
    override fun isStored(key: String) = throw AssertionError()
    override fun store(key: String, value: String) = throw AssertionError()
    override fun retrieve(key: String): String = throw AssertionError()
}

private object DummyHttpClient: HttpClient {
    override fun get(url: String) = throw AssertionError()
}

private object DummyHashService : HashService {
    override fun getHash(string: String) = throw AssertionError()
}

private class HttpClientStub(urlsWithResources: Map<String, List<String>>) : HttpClient {
    private val urlsWithResourceIterators = urlsWithResources.mapValues { it.value.iterator() }

    override fun get(url: String) = urlsWithResourceIterators[url]?.next() ?: throw AssertionError()
}

private class HashServicesStub(private val stringsWithHashedStrings: Map<String, String>) : HashService {
    override fun getHash(string: String) = stringsWithHashedStrings[string] ?: throw AssertionError()
}
