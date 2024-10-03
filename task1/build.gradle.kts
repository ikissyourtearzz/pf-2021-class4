dependencies {
    testImplementation(kotlin("test"))
}

plugins {
    alias(libs.plugins.kotlin.jvm)
    application
}

application {
    mainClass.set("MainKt")
}
