package no.skotbuvel.portal.config

import com.zaxxer.hikari.HikariDataSource
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import javax.sql.DataSource

class DbConfig(databaseConfig: HerokuPostgresConfig) {
    val dataSource: DataSource = HikariDataSource().apply {
        dataSourceClassName = "org.postgresql.ds.PGSimpleDataSource"
        username = databaseConfig.username()
        password = databaseConfig.password()
        addDataSourceProperty("serverName", databaseConfig.host())
        addDataSourceProperty("portNumber", databaseConfig.port())
        addDataSourceProperty("databaseName", databaseConfig.database())
    }

    fun dslContext(): DSLContext = DSL.using(dataSource, SQLDialect.POSTGRES)
}