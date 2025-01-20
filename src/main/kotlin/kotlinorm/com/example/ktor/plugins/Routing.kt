package kotlinorm.com.example.ktor.plugins

import com.kotlinorm.orm.delete.delete
import com.kotlinorm.orm.insert.insert
import com.kotlinorm.orm.select.select
import com.kotlinorm.orm.update.update
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinorm.com.example.ktor.pojos.User
import kotlinx.html.FormMethod
import kotlinx.html.a
import kotlinx.html.body
import kotlinx.html.form
import kotlinx.html.h1
import kotlinx.html.head
import kotlinx.html.hr
import kotlinx.html.li
import kotlinx.html.link
import kotlinx.html.submitInput
import kotlinx.html.table
import kotlinx.html.td
import kotlinx.html.textInput
import kotlinx.html.th
import kotlinx.html.title
import kotlinx.html.tr
import kotlinx.html.ul

fun Application.configureRouting() {
    routing {
        staticResources(
            "/static",
            "static"
        )
        route("/") {
            get {
                call.respondRedirect("/users")
            }
        }
        route("/users") {
            get {
                call.respondHtml {
                    head {
                        link(rel = "stylesheet", href = "/static/styles.css")
                        title("User List")
                    }
                    val pageIndex = call.parameters["pageIndex"]?.toIntOrNull() ?: 1
                    val pageSize = 10
                    val (total, users) = User().select().orderBy { it.id }
                        .page(pageIndex, pageSize).withTotal().queryList()
                    body {
                        h1 { +"User List" }
                        form(action = "/users/create", method = FormMethod.post) {
                            textInput(name = "name") { placeholder = "Enter name" }
                            textInput(name = "age") { placeholder = "Enter age" }
                            submitInput { value = "Create User" }
                        }
                        hr()
                        val pageCount = (total + pageSize - 1) / pageSize
                        ul {
                            for (i in 1..pageCount) {
                                li {
                                    a(href = "/users?pageIndex=$i") { +i.toString() }
                                }
                            }
                        }
                        table {
                            tr {
                                th { +"ID" }
                                th { +"Name" }
                                th { +"Age" }
                                th { +"Actions" }
                            }
                            for (user in users) {
                                tr {
                                    td { +user.id.toString() }
                                    td { +user.name.orEmpty() }
                                    td { +user.age.toString() }
                                    td {
                                        a(href = "/users/edit/${user.id}") { +"Edit" }
                                        a(href = "/users/delete/${user.id}") { +"Delete" }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            post("/create") {
                // 处理用户创建的逻辑
                val name = call.parameters["name"] ?: return@post
                val age = call.parameters["age"]?.toIntOrNull() ?: return@post
                User(name = name, age = age).insert().execute()
                call.respondRedirect("/users")
            }
            get("/edit/{id}") {
                val id = call.parameters["id"]?.toIntOrNull() ?: return@get
                val user = User(id = id).select().queryOne()
                call.respondHtml {
                    head {
                        link(rel = "stylesheet", href = "/static/styles.css")
                        title("Edit User")
                    }
                    body {
                        h1 { +"Edit User" }
                        form(action = "/users/update/${user.id}", method = FormMethod.post) {
                            textInput(name = "name") {
                                value = user.name.orEmpty()
                                placeholder = "Enter name"
                            }
                            textInput(name = "age") {
                                value = user.age?.toString().orEmpty()
                                placeholder = "Enter age"
                            }
                            submitInput { value = "Update User" }
                        }
                    }
                }
            }
            post("/update/{id}") {
                val id = call.parameters["id"]?.toIntOrNull()
                val body = call.receiveParameters()
                val name = body["name"]
                val age = body["age"]?.toIntOrNull()
                if (id != null && name != null && age != null) {
                    User(id = id, name = name, age = age).update {
                        it.name + it.age
                    }.by { it.id }.execute()
                    call.respondRedirect("/users")
                } else {
                    call.respondText("Invalid input", status = HttpStatusCode.BadRequest)
                }
            }
            get("/delete/{id}") {
                val id = call.parameters["id"]?.toIntOrNull() ?: return@get
                User(id = id).delete().execute()
                call.respondRedirect("/users")
            }
        }
    }
}