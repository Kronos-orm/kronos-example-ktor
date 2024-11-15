# kronos-example-ktor

-------------------------

English | [简体中文](https://github.com/Kronos-orm/kronos-example-ktor/blob/main/README-zh_CN.md)

This is a sample project based on Ktor + Kronos ORM + JDK 11 + Gradle + Kotlin 2.0.0(2.0.20 is about to be adapted.)

If you would like to learn more about Kronos, please visit [Kronos](https://www.kotlinorm.com/).

## Introducing Gradle dependencies

**1. Add Kronos dependency**

```kts
dependencies {
    implementation("com.kotlinorm:kronos-core:0.1.0-SNAPSHOT")
    implementation("com.kotlinorm:kronos-jdbc-wrapper:0.1.0-SNAPSHOT")
}
```

**2. Add Kotlin compiler plugin**

```kts
plugins {
    id("com.kotlinorm.kronos-gradle-plugin") version "0.1.0"
}
```

## Configure the data source

The sample project uses kronos-jdbc-wrapper, and you can configure the data source in the following way.
You can also replace it with another wrapper or customize the wrapper.

```kotlin
import com.kotlinorm.Kronos
import com.kotlinorm.KronosBasicWrapper

val ds = BasicDataSource().apply {
    url = "jdbc:mysql://localhost:3306/kotlinorm"
    username = "root"
    password = "**********"
}

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
    Kronos.dataSource = { KronosBasicWrapper(ds) }
}
```

## Run the project

After running the project, visit the following URL to view the results:

```
http://localhost:8081
```

If the interface returns the results shown below, Kronos has run successfully and the compiler plugin is working
properly.

![screen](https://github.com/Kronos-orm/kronos-example-ktor/blob/main/screenshot/img.png?raw=true)
