# kronos-example-ktor

-------------------------

[English](https://github.com/Kronos-orm/kronos-example-ktor/blob/main/README.md) | 简体中文

这是一个基于`Ktor` + `Kronos ORM` + `JDK 17` + `Gradle` + `Kotlin 2.1.0`的简单用户管理系统。

该网站使用MySQL数据库存储用户信息，并提供添加、删除、修改和查询用户等功能，以及分页查询。

在项目中，Kronos-ORM与Ktor完美集成，前端页面使用Html DSL构建。

如果您想了解更多关于Kronos的信息，请访问[Kronos](https://www.kotlinorm.com/)。

## 引入Gradle依赖

**1. 添加Kronos依赖**

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

**2. 添加Kronos编译器插件**

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

## 配置数据源

项目中使用了Kronos-jdbc-wrapper，您可以按以下方式配置数据源，也可以替换为其他wrapper或自定义wrapper。

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
        // 应用启动时，从实体类自动同步表结构
        dataSource.table.syncTable<User>()
    }
    configureRouting()
}
```

## 项目结构

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

## 运行项目

运行项目后，访问以下URL：

```
http://localhost:8081
```

## 项目截图

![screen](https://github.com/Kronos-orm/kronos-example-ktor/blob/main/screenshot/user-list.png?raw=true)
![screen](https://github.com/Kronos-orm/kronos-example-ktor/blob/main/screenshot/edit-user.png?raw=true)