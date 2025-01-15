package kotlinorm.com.example.ktor.plugins

import com.kotlinorm.Kronos
import com.kotlinorm.orm.database.table
import com.kotlinorm.orm.insert.insert
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.routing.*
import kotlinorm.com.example.ktor.pojos.User
import kotlinx.html.body
import kotlinx.html.h1
import kotlinx.html.hr
import kotlinx.html.li
import kotlinx.html.ul

fun Application.configureRouting() {
    Kronos.init{
        dataSource.table.syncTable<User>()
    }
    routing {
        get("/") {
            val user = User(name = randomString(10), age = 18)
            val (_, id) = user.insert().execute()
            call.respondHtml {
                body {
                    h1 {
                        +"New user inserted:"
                    }
                    ul {
                        li { +"id: $id" }
                        li { +"name: ${user.name}" }
                        li { +"age: ${user.age}" }
                    }
                    hr()
                    h1 { +"tableName:${user.kronosTableName()}" }
                    "columns" to user.kronosColumns().forEach {
                        ul {
                            li { +"name: ${it.name}" }
                            li { +"type: ${it.type}" }
                            li { +"nullable: ${it.nullable}" }
                            li { +"primaryKey: ${it.primaryKey.name}" }
                            li { +"comment: ${it.kDoc}" }
                        }
                        hr()
                    }
                }
            }
        }
    }
}

private fun randomString(i: Int): String? {
    val str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
    val random = java.util.Random()
    val sb = StringBuilder()
    for (j in 0 until i) {
        val number = random.nextInt(str.length)
        sb.append(str[number])
    }
    return sb.toString()
}
