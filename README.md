# kronos-example-ktor

-------------------------

English | [简体中文](https://github.com/Kronos-orm/kronos-example-ktor/blob/main/README-zh_CN.md)

This is a simple user management system based on `Ktor` + `Kronos ORM` + `JDK 17` + `Gradle` + `Kotlin 2.1.0`.

The website uses the Mysql database to store user information and provides functions such as adding, deleting, modifying, and querying users, as well as pagination queries.

In the project, Kronos-ORM is perfectly integrated with Ktor, and the front-end pages are built using Html DSL.

If you would like to learn more about Kronos, please visit [Kronos](https://www.kotlinorm.com/).

## Introducing Gradle dependencies

**1. Add Kronos dependency**

```kts
// build.gradle.kts
repositories {
    mavenCentral()
    maven {
        name = "Central Portal Snapshots"
        url = uri("https://central.sonatype.com/repository/maven-snapshots/")
    }
}

dependencies {
    implementation("com.kotlinorm:kronos-core:0.0.2-SNAPSHOT")
    implementation("com.kotlinorm:kronos-jdbc-wrapper:0.0.2-SNAPSHOT")
}
```

**2. Add Kronos compiler plugin**

```kotlin
// settings.gradle.kts
pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven {
            name = "Central Portal Snapshots"
            url = uri("https://central.sonatype.com/repository/maven-snapshots/")
        }
    }
}

// build.gradle.kts
plugins {
    id("com.kotlinorm.kronos-gradle-plugin") version "0.0.2-SNAPSHOT"
}
```

## Configure the data source

The sample project uses kronos-jdbc-wrapper, and you can configure the data source in the following way.
You can also replace it with another wrapper or customize the wrapper.

```kotlin
import com.kotlinorm.Kronos
import com.kotlinorm.KronosBasicWrapper

val pool by lazy {
    KronosBasicWrapper(BasicDataSource().apply {
        url = "jdbc:mysql://localhost:3306/kotlinorm"
        username = "root"
        password = "**********"
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
        // Auto synchronize the table structure from the entity class when the application starts
        dataSource.table.syncTable<User>()
    }
    configureRouting()
}
```

## Project structure

```
├── src
│   └── main
│       ├── kotlin
│       │   └── kotlinorm.com.example.ktor
│       │       ├── plugins
│       │       │   └── Routing.kt
│       │       ├── pojos
│       │       │   └── User.kt
│       │       └── Application.kt
│       ├──resources
│       │  ├── static
│       │  │   └── styles.css
│       │  ├─── application.yaml
│       │  └─── logback.xml
│       └── test
│           └── kotlin
│               └── kotlinorm.com.example.ktor
│                   └── ApplicationTest.kt
├── build.gradle.kts
├── settings.gradle.kts
└── gradle.properties
```

## Running the project

After program startup, you can access the following URL in the browser:

```
http://localhost:8081
```

## Screenshots

![screen](https://github.com/Kronos-orm/kronos-example-ktor/blob/main/screenshot/user-list.png?raw=true)
![screen](https://github.com/Kronos-orm/kronos-example-ktor/blob/main/screenshot/edit-user.png?raw=true)