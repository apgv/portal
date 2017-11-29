package no.skotbuvel.portal

import com.zaxxer.hikari.HikariDataSource
import no.skotbuvel.portal.config.HerokuPostgresConfig
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import javax.sql.DataSource

class DbUtil(databaseConfig: HerokuPostgresConfig) {
    val dataSource: DataSource = HikariDataSource().apply {
        dataSourceClassName = "org.postgresql.ds.PGSimpleDataSource"
        username = databaseConfig.username()
        password = databaseConfig.password()
        schema = "public"
        addDataSourceProperty("serverName", databaseConfig.host())
        addDataSourceProperty("portNumber", databaseConfig.port())
        addDataSourceProperty("databaseName", databaseConfig.database())
    }

    fun dslContext(): DSLContext = DSL.using(dataSource, SQLDialect.POSTGRES)
}