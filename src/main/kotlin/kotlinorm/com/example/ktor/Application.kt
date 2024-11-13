package kotlinorm.com.example.ktor

import com.kotlinorm.Kronos
import com.kotlinorm.KronosBasicWrapper
import com.kotlinorm.beans.config.LineHumpNamingStrategy
import io.ktor.server.application.*
import kotlinorm.com.example.ktor.plugins.configureRouting
import org.apache.commons.dbcp2.BasicDataSource

val ds = BasicDataSource().apply {
    url = "jdbc:mysql://localhost:3306/kotlinorm"
    username = "root"
    password = "**********"
}

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
    Kronos.apply {
        dataSource = { KronosBasicWrapper(ds) }
        fieldNamingStrategy = LineHumpNamingStrategy
        tableNamingStrategy = LineHumpNamingStrategy
    }
}

fun Application.module() {
    configureRouting()
}
