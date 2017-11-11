package no.skotbuvel.portal

import com.zaxxer.hikari.HikariDataSource
import javax.sql.DataSource

object DbUtil {
    val datasource: DataSource = HikariDataSource().apply {
        dataSourceClassName = "org.postgresql.ds.PGSimpleDataSource"
        username = "postgres"
        password = "mysecretpassword"
        schema = "public"
        addDataSourceProperty("databaseName", "postgres")
        addDataSourceProperty("portNumber", "5432")
        addDataSourceProperty("serverName", "172.17.0.2")
    }
}