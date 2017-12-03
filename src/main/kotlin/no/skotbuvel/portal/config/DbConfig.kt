package no.skotbuvel.portal.config

import com.zaxxer.hikari.HikariDataSource
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import javax.sql.DataSource

object DbConfig {
    val dataSource: DataSource = HikariDataSource().apply {
        dataSourceClassName = "org.postgresql.ds.PGSimpleDataSource"
        username = HerokuPostgresConfig.username()
        password = HerokuPostgresConfig.password()
        addDataSourceProperty("serverName", HerokuPostgresConfig.host())
        addDataSourceProperty("portNumber", HerokuPostgresConfig.port())
        addDataSourceProperty("databaseName", HerokuPostgresConfig.database())
    }

    fun dslContext(): DSLContext = DSL.using(dataSource, SQLDialect.POSTGRES)
}