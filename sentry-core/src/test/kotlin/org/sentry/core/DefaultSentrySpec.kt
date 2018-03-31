package org.sentry.core

import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import com.natpryce.hamkrest.throws
import kotlinx.coroutines.experimental.runBlocking
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.sentry.api.CheckResult
import org.sentry.storage.api.inMemoryKeyValueStorage

object DefaultSentrySpec : Spek({
    given("a sentry") {
        on("adding a blank url") {
            it("throws illegal argument exception") {
                DefaultSentry(
                    keyValueStorage = dummyKeyValueStorage(),
                    httpClient = dummyHttpClient(),
                    hashService = dummyHashService()
                ).apply {
                    assert.that({ runBlocking { add("") } }, throws<IllegalArgumentException>())
                    assert.that({ runBlocking { add(" ") } }, throws<IllegalArgumentException>())
                    assert.that({ runBlocking { add("    ") } }, throws<IllegalArgumentException>())
                }
            }
        }
        on("checking an url without adding it first") {
            it("throws illegal state exception") {
                DefaultSentry(
                    keyValueStorage = inMemoryKeyValueStorage(),
                    httpClient = dummyHttpClient(),
                    hashService = dummyHashService()
                ).apply {
                    assert.that({ runBlocking { check("") } }, throws<IllegalStateException>())
                }
            }
        }
        on("checking an url when resource not changed") {
            it("returns unchanged result") {
                DefaultSentry(
                    keyValueStorage = inMemoryKeyValueStorage(),
                    httpClient = httpClientStub("url" to listOf("resource", "resource")),
                    hashService = hashServiceStub("resource" to "hashedResource")
                ).apply { runBlocking {
                    add("url")
                    assert.that(runBlocking { check("url") }, equalTo(CheckResult.UNCHANGED))
                } }
            }
        }
        on("checking an url multiple times when resource not changed") {
            it("returns unchanged results") {
                DefaultSentry(
                    keyValueStorage = inMemoryKeyValueStorage(),
                    httpClient = httpClientStub("url" to listOf("resource", "resource", "resource")),
                    hashService = hashServiceStub("resource" to "hashedResource")
                ).apply { runBlocking {
                    add("url")
                    assert.that(runBlocking { check("url") }, equalTo(CheckResult.UNCHANGED))
                    assert.that(runBlocking { check("url") }, equalTo(CheckResult.UNCHANGED))
                } }
            }
        }
        on("checking an url when resource changed") {
            it("returns changed result") {
                DefaultSentry(
                    keyValueStorage = inMemoryKeyValueStorage(),
                    httpClient = httpClientStub("url" to listOf("resource", "changedResource")),
                    hashService = hashServiceStub(
                        "resource" to "hashedResource",
                        "changedResource" to "hashedChangedResource"
                    )
                ).apply { runBlocking {
                    add("url")
                    assert.that(runBlocking { check("url") }, equalTo(CheckResult.CHANGED))
                } }
            }
        }
        on("checking an url multiple times when resource changed") {
            it("returns changed results") {
                DefaultSentry(
                    keyValueStorage = inMemoryKeyValueStorage(),
                    httpClient = httpClientStub(
                        "url" to listOf("resource", "changedResource", "changedResource")
                    ),
                    hashService = hashServiceStub(
                        "resource" to "hashedResource",
                        "changedResource" to "hashedChangedResource"
                    )
                ).apply { runBlocking {
                    add("url")
                    assert.that(runBlocking { check("url") }, equalTo(CheckResult.CHANGED))
                    assert.that(runBlocking { check("url") }, equalTo(CheckResult.CHANGED))
                } }
            }
        }
    }
})
