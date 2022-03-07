plugins {
    id("org.springframework.boot")
    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {
    implementation(Lib.SPRING_BOOT_STARTER_WEBFLUX)
    implementation(Lib.JACKSON_MODULE_KOTLIN)
    implementation(Lib.REACTOR_KOTLIN_EXTENSION)
    implementation(Lib.KOTLIN_REFLECT)
    implementation(Lib.KOTLINX_COROUTINES_REACTOR)
    testImplementation(Lib.SPRING_BOOT_STARTER_TEST)
    testImplementation(Lib.REACTOR_TEST)
}
