package org.sentry.storage.api

interface KeyValueStorage {
    fun isStored(key: String): Boolean
    fun store(key: String, value: String)
    fun retrieve(key: String): String
}
