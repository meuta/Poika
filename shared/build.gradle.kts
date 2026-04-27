plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
    id("org.jetbrains.kotlin.plugin.compose")
}

kotlin {

    androidTarget()
    jvm("desktop")

    sourceSets {
        val desktopMain by getting

        commonMain.dependencies {
            // Gson TODO: replace with kotlinx.serialization later
            implementation("com.google.code.gson:gson:2.13.2")

            // Coroutines
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2")

            // Material
            implementation("org.jetbrains.compose.material3:material3:1.9.0")
            implementation("org.jetbrains.compose.material:material-icons-extended:1.7.3")

            // Res
            implementation("org.jetbrains.compose.components:components-resources:1.10.3")

            implementation("org.jetbrains.compose.runtime:runtime:1.10.3")
            implementation("org.jetbrains.compose.foundation:foundation:1.10.3")

            // Coil
            implementation("io.coil-kt.coil3:coil-compose:3.3.0")

            // Preview
            implementation("androidx.compose.ui:ui-tooling-preview:1.10.1")
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }

        androidMain.dependencies {}

        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.10.2")
        }
    }
}

android {
    namespace = "com.obrigada_eu.poika.shared"
    compileSdk = 36
    defaultConfig {
        minSdk = 24
    }
}

compose.desktop {
    application {
        mainClass = "com.obrigada_eu.poika.desktop.PoikaDesktopAppKt"
    }
}

compose.resources {
    publicResClass = true
}