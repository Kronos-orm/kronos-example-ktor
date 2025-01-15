import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotlin_version: String by project
val kotlinx_html_version: String by project
val logback_version: String by project

plugins {
    kotlin("jvm") version "2.1.0"
    id("io.ktor.plugin") version "3.0.1"
    id("com.kotlinorm.kronos-gradle-plugin") version "0.0.1"
}

dependencies {
    implementation("com.kotlinorm:kronos-core:0.0.1")
    implementation("com.kotlinorm:kronos-jdbc-wrapper:0.0.1")
    implementation("org.apache.commons:commons-dbcp2:2.12.0")
    implementation("com.mysql:mysql-connector-j:8.4.0")
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-html-builder-jvm")
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:$kotlinx_html_version")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-config-yaml")
    testImplementation("io.ktor:ktor-server-test-host-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}

group = "kotlinorm.com"
version = "0.0.1"

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