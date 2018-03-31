package org.sentry.core.hash

import org.sentry.core.hash.api.HashService
import java.security.MessageDigest
import java.util.*

class Sha256HashService : HashService {
    override fun getHash(string: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val byteArray = string.toByteArray()
        val hashedByteArray = digest.digest(byteArray)
        return String(Base64.getEncoder().encode(hashedByteArray))
    }
}
