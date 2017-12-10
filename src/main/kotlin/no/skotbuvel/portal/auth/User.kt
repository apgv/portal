package no.skotbuvel.portal.auth

import com.auth0.jwt.interfaces.DecodedJWT

data class User(val subject: String, val email: String)

fun userFromJWT(decodedJWT: DecodedJWT): User {
    return User(
            subject = decodedJWT.subject,
            email = JwtUtil.email(decodedJWT)
    )
}
