package no.skotbuvel.portal.helper

import com.zaxxer.hikari.HikariDataSource
import no.skotbuvel.portal.config.HerokuPostgresConfig
import org.jooq.DSLContext
import org.jooq.SQLDialect
import org.jooq.impl.DSL
import javax.sql.DataSource

class DbHelper(private val herokuPostgresConfig: HerokuPostgresConfig) {
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