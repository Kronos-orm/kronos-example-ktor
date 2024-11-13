package kotlinorm.com.example.ktor.plugins

import com.kotlinorm.kronosSpringDemo.pojos.User
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.html.body
import kotlinx.html.h1
import kotlinx.html.hr
import kotlinx.html.li
import kotlinx.html.ul

fun Application.configureRouting() {
    routing {
        val user = User()
        get("/") {
            call.respondHtml {
                body {
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
