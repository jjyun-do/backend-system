plugins {
    id("org.springframework.boot")
    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {
    implementation(Lib.SPRING_BOOT_STARTER_WEBFLUX)
    implementation(Lib.SPRING_BOOT_STARTER_SECURITY)
    implementation(Lib.SPRING_BOOT_STARTER_OAUTH2_CLIENT)
    implementation(Lib.JACKSON_MODULE_KOTLIN)
    implementation(Lib.REACTOR_KOTLIN_EXTENSION)
    implementation(Lib.KOTLIN_REFLECT)
    implementation(Lib.KOTLINX_COROUTINES_REACTOR)
    implementation(Lib.DATA_R2DBC)
    implementation(Lib.FLYWAY_CORE)
    implementation(Lib.R2DBC_POSTGRESQL)
    runtimeOnly(Lib.JDBC_POSTGRESQL)
    annotationProcessor(Lib.SPRING_BOOT_CONFIGURATION_PROCESSOR)
    testImplementation(Lib.SPRING_BOOT_STARTER_TEST)
    testImplementation(Lib.REACTOR_TEST)
    testImplementation(Lib.MOCKK)
}
