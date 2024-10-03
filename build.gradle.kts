import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        mavenCentral()
    }
}

plugins {
    alias(libs.plugins.kotlin.jvm) apply false
}

allprojects {
    group = "ru.spbu.math-cs"
    version = "1.0"

    repositories {
        mavenCentral()
    }

    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_21.toString()
        }
    }

    tasks.withType<Test>().configureEach {
        // Enable JUnit 5 (Gradle 4.6+).
        useJUnitPlatform()
    }
}
