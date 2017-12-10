package no.skotbuvel.portal.auth

import com.auth0.jwt.interfaces.DecodedJWT

data class JWTUser(val subject: String, val email: String)

fun userFromJWT(decodedJWT: DecodedJWT) =
        JWTUser(
                subject = decodedJWT.subject,
                email = JwtUtil.email(decodedJWT)
        )
