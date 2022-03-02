plugins {
    id("org.springframework.boot")
    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux:2.6.4")
    // implementation("org.springframework.boot:spring-boot-starter-data-r2dbc:2.6.3")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.1")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions:1.1.5")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.6.10")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.6.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test:2.6.4")
    testImplementation("io.projectreactor:reactor-test:3.4.15")
}
