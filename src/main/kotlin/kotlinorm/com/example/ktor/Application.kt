package kotlinorm.com.example.ktor

import com.kotlinorm.Kronos
import com.kotlinorm.KronosBasicWrapper
import com.kotlinorm.orm.database.table
import io.ktor.server.application.*
import kotlinorm.com.example.ktor.plugins.configureRouting
import kotlinorm.com.example.ktor.pojos.User
import org.apache.commons.dbcp2.BasicDataSource

val pool by lazy {
    KronosBasicWrapper(BasicDataSource().apply {
        url = "jdbc:mysql://localhost:3306/kronos"
        username = "root"
        password = "******"
    })
}


fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    Kronos.init {
        dataSource = { pool }
        fieldNamingStrategy = lineHumpNamingStrategy
        tableNamingStrategy = lineHumpNamingStrategy
        dataSource.table.syncTable<User>()
    }
    configureRouting()
}