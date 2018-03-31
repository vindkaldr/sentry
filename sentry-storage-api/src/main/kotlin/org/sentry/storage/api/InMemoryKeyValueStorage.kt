package org.sentry.storage.api

fun inMemoryKeyValueStorage(): KeyValueStorage = InMemoryKeyValueStorage()

private class InMemoryKeyValueStorage : KeyValueStorage {
    private val storedKeyAndValues = mutableMapOf<String, String>()

    override fun isStored(key: String): Boolean {
        return storedKeyAndValues.containsKey(key)
    }

    override fun store(key: String, value: String) {
        storedKeyAndValues[key] = value
    }

    override fun retrieve(key: String): String {
        check(isStored(key))
        return storedKeyAndValues[key] ?: throw IllegalArgumentException()
    }
}
