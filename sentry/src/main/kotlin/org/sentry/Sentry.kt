package org.sentry

import org.sentry.api.Sentry
import org.sentry.core.DefaultSentry
import org.sentry.core.hash.Sha256HashService
import org.sentry.httpclient.DefaultHttpClient
import org.sentry.storage.api.KeyValueStorage

fun sentry(keyValueStorage: KeyValueStorage): Sentry = DefaultSentry(
    keyValueStorage = keyValueStorage,
    httpClient = DefaultHttpClient(),
    hashService = Sha256HashService()
)
