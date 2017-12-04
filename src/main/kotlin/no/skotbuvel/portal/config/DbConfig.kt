package no.skotbuvel.portal.config

import com.zaxxer.hikari.HikariDataSource
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import javax.sql.DataSource

class DbConfig(private val herokuPostgresConfig: HerokuPostgresConfig) {
    val dataSource: DataSource = HikariDataSource().apply {
        dataSourceClassName = "org.postgresql.ds.PGSimpleDataSource"
        username = herokuPostgresConfig.username()
        password = herokuPostgresConfig.password()
        addDataSourceProperty("serverName", herokuPostgresConfig.host())
        addDataSourceProperty("portNumber", herokuPostgresConfig.port())
        addDataSourceProperty("databaseName", herokuPostgresConfig.database())
    }

    fun dslContext(): DSLContext = DSL.using(dataSource, SQLDialect.POSTGRES)
}