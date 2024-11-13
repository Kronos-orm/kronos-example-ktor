package kotlinorm.com

import com.kotlinorm.Kronos
import com.kotlinorm.KronosBasicWrapper
import com.kotlinorm.beans.config.LineHumpNamingStrategy
import io.ktor.server.application.*
import kotlinorm.com.plugins.*
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
