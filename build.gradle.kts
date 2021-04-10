// https://kotlinlang.org/docs/js-project-setup.html

plugins {
    id("org.jetbrains.kotlin.multiplatform") version "1.4.30"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.4.30"
    id("dev.fritz2.fritz2-gradle") version "0.9.1"
}

repositories {
    mavenCentral()
}

kotlin {
    jvm()
    // todo: having issues with IR backend
    js(LEGACY) {
        browser()
    }.binaries
        .executable()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("dev.fritz2:core:0.9.1")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.1.0")
            }
        }
        val jvmMain by getting {
            dependencies {
            }
        }
        val jsMain by getting {
            dependencies {
            }
        }
    }
}