package no.skotbuvel.portal.auth

import com.auth0.jwt.interfaces.Claim
import com.auth0.jwt.interfaces.DecodedJWT
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.util.*

internal class JwtUtilTest {

    private val decodedJWT: DecodedJWT = mock(DecodedJWT::class.java)

    @BeforeEach
    internal fun setUp() {
        `when`(decodedJWT.getClaim("email")).thenReturn(TestClaim())
    }

    @Test
    fun email() {
        assertThat(JwtUtil.email(decodedJWT)).isEqualTo("foobar@example.com")
    }

    private class TestClaim : Claim {
        override fun isNull(): Boolean {
            TODO("not implemented")
        }

        override fun asDate(): Date {
            TODO("not implemented")
        }

        override fun asMap(): MutableMap<String, Any> {
            TODO("not implemented")
        }

        override fun <T : Any?> asList(tClazz: Class<T>?): MutableList<T> {
            TODO("not implemented")
        }

        override fun asLong(): Long {
            TODO("not implemented")
        }

        override fun <T : Any?> `as`(tClazz: Class<T>?): T {
            TODO("not implemented")
        }

        override fun asBoolean(): Boolean {
            TODO("not implemented")
        }

        override fun asDouble(): Double {
            TODO("not implemented")
        }

        override fun asString(): String {
            return "foobar@example.com"
        }

        override fun <T : Any?> asArray(tClazz: Class<T>?): Array<T> {
            TODO("not implemented")
        }

        override fun asInt(): Int {
            TODO("not implemented")
        }
    }

}