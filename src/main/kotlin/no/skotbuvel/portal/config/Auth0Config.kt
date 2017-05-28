package no.skotbuvel.portal.config

data class Auth0Config(
        val domain: String,
        val clientID: String,
        val redirectUri: String,
        val audience: String,
        val responseType: String,
        val scope: String
) {
    companion object {
        const val DOMAIN = "auth0.domain"
        const val CLIENT_ID = "auth0.clientID"
        const val REDIRECT_URI = "auth0.redirectUri"
        const val AUDIENCE = "auth0.audience"
        const val RESPONSE_TYPE = "auth0.responseType"
        const val SCOPE = "auth0.scope"
    }

}