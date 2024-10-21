import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinSerialization)
    id("dev.mokkery") version "2.4.0"
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        instrumentedTestVariant.sourceSetTree.set(KotlinSourceSetTree.test)
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.decompose)
            implementation(libs.decompose.extensions.compose)
            implementation(libs.decompose)
            implementation(libs.decompose.extensions.compose)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.materialIconsExtended)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            implementation(libs.decompose)
            implementation(libs.decompose.extensions.compose)

            implementation(libs.ktor.client.core)
            implementation(libs.ktor.content.neg)
            implementation(libs.ktor.content.serial)
            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor3)


            implementation ("com.arkivanov.mvikotlin:mvikotlin:4.2.0")
            implementation ("com.arkivanov.mvikotlin:mvikotlin-main:4.2.0")
            implementation ("com.arkivanov.mvikotlin:mvikotlin-logging:4.2.0")
            implementation ("com.arkivanov.mvikotlin:mvikotlin-extensions-coroutines:4.2.0")
            implementation ("com.arkivanov.mvikotlin:mvikotlin-extensions-reaktive:4.2.0")

        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(kotlin("test-annotations-common"))

//            @OptIn(ExperimentalComposeLibrary::class)
//            implementation(compose.uiTest)
        }


        androidMain.dependencies {
            implementation(compose.uiTooling)
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.client.okhttp)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }

    }
}

android {
    namespace = "com.testtask.project"
    compileSdk = 35

    defaultConfig {
        minSdk = 21
        targetSdk = 35

        applicationId = "com.testtask.project.androidApp"
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions{
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.compose.ui.test.junit4.android)
    debugImplementation(libs.compose.ui.test.manifest)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")

}
