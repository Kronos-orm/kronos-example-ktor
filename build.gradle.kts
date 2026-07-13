import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlinVersion = providers.gradleProperty("kotlin_version").get()
val kotlinxHtmlVersion = providers.gradleProperty("kotlinx_html_version").get()
val logbackVersion = providers.gradleProperty("logback_version").get()

allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
    }
}

plugins {
    kotlin("jvm") version "2.4.0"
    id("io.ktor.plugin") version "3.0.1"
    id("com.kotlinorm.kronos-gradle-plugin") version "0.2.3"
}

dependencies {
    implementation("com.kotlinorm:kronos-core:0.2.3")
    implementation("com.kotlinorm:kronos-jdbc-wrapper:0.2.3")
    implementation("org.apache.commons:commons-dbcp2:2.12.0")
    implementation("com.mysql:mysql-connector-j:8.4.0")
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-html-builder-jvm")
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:$kotlinxHtmlVersion")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("io.ktor:ktor-server-config-yaml")
    testImplementation("io.ktor:ktor-server-test-host-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
}

group = "kotlinorm.com"
version = "0.2.3"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenLocal()
    mavenCentral()
}

tasks.withType<KotlinCompile> {
    compilerOptions {
        verbose = true
    }
}
