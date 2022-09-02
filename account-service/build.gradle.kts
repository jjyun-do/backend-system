plugins {
    id(Plugins.SPRING_BOOT.name)
    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {
    implementation(Libs.SPRING_BOOT_STARTER_WEBFLUX)
    implementation(Libs.SPRING_BOOT_STARTER_AOP)
    implementation(Libs.SPRING_BOOT_STARTER_MAIL)
    implementation(Libs.JACKSON_MODULE_KOTLIN)
    implementation(Libs.REACTOR_KOTLIN_EXTENSION)

    testImplementation(Libs.SPRING_BOOT_STARTER_TEST)
    testImplementation(Libs.REACTOR_TEST)
    testImplementation(Libs.MOCKK)
}
