package org.sentry.core

import org.sentry.api.CheckResult
import org.sentry.api.Sentry
import org.sentry.core.hash.api.HashService
import org.sentry.httpclient.api.HttpClient
import org.sentry.storage.api.KeyValueStorage

class DefaultSentry(
    private val keyValueStorage: KeyValueStorage,
    private val httpClient: HttpClient,
    private val hashService: HashService
) : Sentry {
    override suspend fun add(url: String) {
        require(url.isNotBlank())
        keyValueStorage.store(url, getHashedResource(url))
    }

    override suspend fun check(url: String): CheckResult {
        check(keyValueStorage.isStored(url))
        return if (isResourceChanged(url)) {
            CheckResult.CHANGED
        }
        else {
            CheckResult.UNCHANGED
        }
    }

    private fun isResourceChanged(url: String) = keyValueStorage.retrieve(url) != getHashedResource(url)
    
    private fun getHashedResource(url: String) = hashService.getHash(httpClient.get(url))
}
