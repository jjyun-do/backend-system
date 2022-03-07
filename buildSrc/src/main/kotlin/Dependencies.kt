/**
 * To define Versions
 */
object Version {
    const val SPRING_BOOT = "2.6.4"
    const val GRADLE_PLUGIN = "7.4"
    const val KOTLIN = "1.6.10"

    const val DEPENDENCY_MANAGEMENT = "1.0.11.RELEASE"
    const val R2DBC = "2.6.3"
    const val KTLINT = "10.2.1"
    const val DETEKT = "10.20.0-RC1"
    const val JACKSON = "2.13.1"
    const val REACTOR_TEST = "3.4.15"

    const val KOTLIN_REACTOR_EXTENSION = "1.1.5"
    const val KOTLIN_REFLECT = "1.6.10"
    const val KOTLIN_COROUTINES_REACTOR = "1.6.0"
}

/**
 * To define Plugins
 */
object Plugin {
    const val SPRING_BOOT = "org.springframework.boot"
    const val DEPENDENCY_MANAGEMENT = "io.spring.dependency-management"
    const val GRADLE_KTLINT = "org.jlleitschuh.gradle.ktlint"
    const val ARTURBOSCH_DETEKT = "io.gitlab.arturbosch.detekt"
}

/**
 * To define libraries
 */
object Lib {
    const val SPRING_BOOT_STARTER_WEBFLUX =
        "org.springframework.boot:spring-boot-starter-webflux:${Version.SPRING_BOOT}"
    const val JACKSON_MODULE_KOTLIN = "com.fasterxml.jackson.module:jackson-module-kotlin:${Version.JACKSON}"
    const val REACTOR_KOTLIN_EXTENSION =
        "io.projectreactor.kotlin:reactor-kotlin-extensions:${Version.KOTLIN_REACTOR_EXTENSION}"
    const val KOTLIN_REFLECT = "org.jetbrains.kotlin:kotlin-reflect:${Version.KOTLIN_REFLECT}"
    const val KOTLINX_COROUTINES_REACTOR =
        "org.jetbrains.kotlinx:kotlinx-coroutines-reactor:${Version.KOTLIN_COROUTINES_REACTOR}"
    const val DATA_R2DBC = "org.springframework.boot:spring-boot-starter-data-r2dbc:${Version.R2DBC}"

    const val SPRING_BOOT_STARTER_TEST = "org.springframework.boot:spring-boot-starter-test:${Version.SPRING_BOOT}"
    const val REACTOR_TEST = "io.projectreactor:reactor-test:${Version.REACTOR_TEST}"
}

/**
 * To define JVM config
 */
object Jvm {
    const val SOURCE_COMPATIBILITY = "17"
    const val TARGET_COMPATIBILITY = "17"
    const val JVM_TARGET = "17"
}
