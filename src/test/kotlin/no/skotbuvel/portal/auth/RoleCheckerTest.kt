package no.skotbuvel.portal.auth

import no.skotbuvel.portal.auth.Role.BOARD_MEMBER
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class RoleCheckerTest {

    @Test
    internal fun hasRole() {
        assertThat(RoleChecker.hasRole(BOARD_MEMBER, listOf("board_member"))).isTrue()
    }

    @Test
    internal fun hasRoleIgnoreCasing() {
        assertThat(RoleChecker.hasRole(BOARD_MEMBER, listOf("BOARD_MEMBER"))).isTrue()
    }

    @Test
    internal fun missesRole() {
        assertThat(RoleChecker.hasRole(BOARD_MEMBER, listOf("role"))).isFalse()
    }
}

