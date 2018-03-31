package org.sentry.core.hash

import com.natpryce.hamkrest.assertion.assert
import com.natpryce.hamkrest.equalTo
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on

object Sha256HashServiceTest : Spek({
    given("a sha256 hash service") {
        on("getting the hash for a string") {
            it("returns the string's base64 encoded sha256 hash value") {
                assert.that(
                    actual = Sha256HashService().getHash("string"),
                    criteria = equalTo("RzKH+CmNunFjqJeQiVj3wOrnM+JdLgJ5kuou3JvtL6g=")
                )
            }
        }
    }
})
