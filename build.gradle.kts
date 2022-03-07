import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.6.4" apply false
    id("io.spring.dependency-management") version "1.0.11.RELEASE" apply false
    id("org.jlleitschuh.gradle.ktlint") version "10.2.1" apply false
    id("io.gitlab.arturbosch.detekt") version "1.20.0-RC1" apply false

    kotlin("jvm") version Version.KOTLIN apply false
    kotlin("plugin.spring") version Version.KOTLIN apply false
}

allprojects {
    group = "com.samsung.healthcare"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }

    tasks.withType<JavaCompile> {
        sourceCompatibility = Jvm.SOURCE_COMPATIBILITY
        targetCompatibility = Jvm.TARGET_COMPATIBILITY
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = Jvm.JVM_TARGET
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

subprojects {
    apply {
        plugin("io.spring.dependency-management")
        plugin("org.jlleitschuh.gradle.ktlint")
        plugin("io.gitlab.arturbosch.detekt")
    }
}
