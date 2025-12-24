plugins {
    kotlin("jvm") version "1.9.22"
    application
    id("org.openjfx.javafxplugin") version "0.1.0"
}

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(17)
}

javafx {
    version = "21"
    modules = listOf(
        "javafx.controls",
        "javafx.graphics"
    )
}

application {
    mainClass.set("app.SudokuAppKt")
}

dependencies {
    implementation(kotlin("stdlib"))
}
