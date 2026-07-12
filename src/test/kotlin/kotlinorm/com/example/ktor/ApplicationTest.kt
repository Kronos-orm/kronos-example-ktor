package kotlinorm.com.example.ktor

import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlinorm.com.example.ktor.plugins.configureRouting
import kotlin.test.*

class ApplicationTest {
    @Test
    fun rootRedirectsToUsers() = testApplication {
        application {
            configureRouting()
        }
        val client = createClient {
            followRedirects = false
        }

        client.get("/").apply {
            assertEquals(HttpStatusCode.Found, status)
            assertEquals("/users", headers[HttpHeaders.Location])
        }
    }
}
