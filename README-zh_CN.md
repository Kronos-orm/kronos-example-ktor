# kronos-example-ktor

-------------------------

[English](https://github.com/Kronos-orm/kronos-example-ktor/blob/main/README.md) | 简体中文

这是一个基于 Ktor + Kronos ORM + JDK 11 + Gradle + Kotlin 2.0.0（2.0.20 即将适配）的示例项目。

如果您想了解更多关于Kronos的信息，请访问[Kronos](https://www.kotlinorm.com/)。

## 引入Gradle依赖

**1. 添加Kronos依赖**

```kts
dependencies {
    implementation("com.kotlinorm:kronos-core:0.1.0-SNAPSHOT")
    implementation("com.kotlinorm:kronos-jdbc-wrapper:0.1.0-SNAPSHOT")
}
```

**2. 添加Kotlin编译器插件**

```kts
plugins {
    id("com.kotlinorm.kronos-gradle-plugin") version "0.1.0"
}
```

## 配置数据源

项目中使用了Kronos-jdbc-wrapper，您可以按以下方式配置数据源，也可以替换为其他wrapper或自定义wrapper。

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

## 运行项目

运行项目后，访问以下URL，即可查看结果：

```
http://localhost:8081
```

如果接口返回的结果如下图所示，则表示Kronos已成功运行，编译器插件也已正常工作。

![screen](https://github.com/Kronos-orm/kronos-example-ktor/blob/main/screenshot/img.png?raw=true)
