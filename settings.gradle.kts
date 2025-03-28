rootProject.name = "kronos-ktor-demo"

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven {
            name = "Central Portal Snapshots"
            url = uri("https://central.sonatype.com/repository/maven-snapshots/")
        }
    }
}