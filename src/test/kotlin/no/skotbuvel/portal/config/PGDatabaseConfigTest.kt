package no.skotbuvel.portal.config

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import java.net.URI

internal class PGDatabaseConfigTest {

    private val pgDatabaseConfig = PGDatabaseConfig(uri = URI("postgres://user:password@localhost:5432/database"))

    @Test
    internal fun name() {
        assertThat(pgDatabaseConfig.username()).isEqualTo("user")
    }

    @Test
    internal fun password() {
        assertThat(pgDatabaseConfig.password()).isEqualTo("password")
    }

    @Test
    internal fun host() {
        assertThat(pgDatabaseConfig.host()).isEqualTo("localhost")
    }

    @Test
    internal fun port() {
        assertThat(pgDatabaseConfig.port()).isEqualTo(5432)
    }

    @Test
    internal fun database() {
        assertThat(pgDatabaseConfig.database()).isEqualTo("database")
    }
}