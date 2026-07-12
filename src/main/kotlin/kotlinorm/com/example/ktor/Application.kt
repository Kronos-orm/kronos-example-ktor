package kotlinorm.com.example.ktor

import com.kotlinorm.Kronos
import com.kotlinorm.orm.ddl.table
import com.kotlinorm.wrappers.KronosJdbcWrapper
import io.ktor.server.application.*
import kotlinorm.com.example.ktor.plugins.configureRouting
import kotlinorm.com.example.ktor.pojos.User
import org.apache.commons.dbcp2.BasicDataSource

val pool by lazy {
    KronosJdbcWrapper(BasicDataSource().apply {
        driverClassName = System.getenv("MYSQL_DRIVER") ?: "com.mysql.cj.jdbc.Driver"
        url = System.getenv("MYSQL_JDBC_URL") ?: "jdbc:mysql://localhost:3306/kronos_testing"
        username = System.getenv("MYSQL_USERNAME") ?: "kronos"
        password = System.getenv("MYSQL_PASSWORD") ?: "kronos"
    })
}


fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    with(Kronos) {
        dataSource = { pool }
        fieldNamingStrategy = lineHumpNamingStrategy
        tableNamingStrategy = lineHumpNamingStrategy
    }
    pool.table.syncTable<User>()
    configureRouting()
}
