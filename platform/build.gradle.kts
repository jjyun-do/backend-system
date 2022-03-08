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
    implementation(Lib.DATA_R2DBC)
    runtimeOnly(Lib.R2DBC_POSTGRESQL)
    testImplementation(Lib.SPRING_BOOT_STARTER_TEST)
    testImplementation(Lib.REACTOR_TEST)
}
