package org.sentry

import org.sentry.api.Fizz

class DefaultFizz : Fizz {
    override fun fuzz() {
        println("fuzz")
    }
}
