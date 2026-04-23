plugins {
    id("org.jetbrains.kotlin.multiplatform")
    id("com.android.library")
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
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }

        androidMain.dependencies {}
        desktopMain.dependencies {}
    }
}

android {
    namespace = "com.obrigada_eu.poika.shared"
    compileSdk = 36
    defaultConfig {
        minSdk = 24
    }
}