import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig.Mode.DEVELOPMENT

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
        browser {
            webpackTask {
                sourceMaps = (mode == DEVELOPMENT)
            }
        }
    }.binaries
        .executable()

    sourceSets {
        val commonMain by getting {
            dependencies {
                // https://github.com/jwstegemann/fritz2
                implementation("dev.fritz2:core:0.9.2")
                implementation("dev.fritz2:components:0.9.2")
                // https://github.com/Kotlin/kotlinx.serialization
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.1.0")
                // https://github.com/LighthouseGames/KmLogging
                implementation("org.lighthousegames:logging:1.0.0")
            }
        }
        val jvmMain by getting {
            dependencies {
            }
        }
        val jsMain by getting {
            // https://kotlinlang.org/docs/using-packages-from-npm.html
            dependencies {
                // https://www.npmjs.com/package/sprintf-js
                implementation(npm("sprintf-js", "1.1.2"))
            }
        }

        all {
            languageSettings
                .useExperimentalAnnotation("kotlin.RequiresOptIn")
        }
    }
}
