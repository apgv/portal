package no.skotbuvel.portal.auth

import com.auth0.jwk.UrlJwkProvider
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import spark.Request
import java.security.interfaces.RSAPublicKey

object JwtUtil {
    private val AUTH0_URL = "https://skotbuvel.eu.auth0.com/"
    private val urlJwkProvider = UrlJwkProvider(AUTH0_URL)
    val AUTH0_ROLES = "https://portal.skotbuvel.no/roles"

    fun verifyAndDecode(request: Request): DecodedJWT {
        val jwt = request.headers("X-JWT")
        val decodedJWT = JWT.decode(jwt)
        val jwk = urlJwkProvider.get(decodedJWT.keyId)
        val publicKey = jwk.publicKey
        val algorithm = Algorithm.RSA256(publicKey as RSAPublicKey, null)
        val jwtVerifier = JWT.require(algorithm)
                .withIssuer(AUTH0_URL)
                .build()
        return jwtVerifier.verify(jwt)
    }

    fun email(decodedJWT: DecodedJWT): String {
        return decodedJWT.getClaim("email").asString()
    }

    fun roles(decodedJWT: DecodedJWT): List<Role> {
        val roleClaims = decodedJWT.getClaim(AUTH0_ROLES)
        val roles = roleClaims.asList(String::class.java)
        return roles.map { s -> Role.valueOfIgnoreCase(s) }
    }
}
