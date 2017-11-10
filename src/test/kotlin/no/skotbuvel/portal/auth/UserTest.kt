package no.skotbuvel.portal.auth

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class UserTest {

    @Test
    fun hasRole() {
        val user = User(subject = "subject", email = "email", roles = listOf(Role.BOARD_MEMBER))

        assertThat(user.hasRole(Role.BOARD_MEMBER)).isTrue()
        assertThat(user.hasRole(Role.ADMIN)).isFalse()
    }

}