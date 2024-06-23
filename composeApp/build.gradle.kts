import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)

    // kotlinx serialization plugin
    kotlin("plugin.serialization") version "1.9.22"
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
//    androidTarget {
//        @OptIn(ExperimentalKotlinGradlePluginApi::class)
//        compilerOptions {
//            jvmTarget.set(JvmTarget.JVM_11)
//        }
//    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    sourceSets {
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)

            implementation(libs.ktor.client.android)
            implementation(libs.koin.android)

            // okhttp for socket
            implementation(libs.okhttp)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)

            // kotlinx serialization
            implementation(libs.kotlinx.serialization.json)

            // kotlinx coroutines
            implementation(libs.kotlinx.coroutines.core)

            // ktor - HTTP client for retrieving data over the internet
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)

            // kamel
            implementation(libs.kamel.image)

            // landscapist (coil3)
            implementation("com.github.skydoves:landscapist-coil3:2.3.4")
            implementation("com.github.skydoves:landscapist-placeholder:2.3.4")
            implementation("com.github.skydoves:landscapist-animation:2.3.4")

            // voyager
            implementation(libs.voyager.navigator)
            implementation(libs.voyager.tabNavigator)
            implementation(libs.voyager.transitions)
            implementation(libs.voyager.bottomSheetNavigator)
            implementation(libs.voyager.koin)
            implementation(libs.voyager.screenModel)

            // koin
            implementation(libs.koin.core)

            // kmp google auth
            implementation(libs.kmpauth.google)
            implementation(libs.kmpauth.uihelper)

            // haze blur
            implementation(libs.haze)

            // video player
            implementation(libs.compose.multiplatform.media.player)

            // cupertino
            implementation(libs.cupertino)
            implementation(libs.cupertino.native)

            // kotlinx-datetime
            implementation(libs.kotlinx.datetime)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}

android {
    namespace = "com.sb1703.moviebooking"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "com.sb1703.moviebooking"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_11
//        targetCompatibility = JavaVersion.VERSION_11
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        compose = true
    }
    dependencies {
        debugImplementation(compose.uiTooling)
    }
}

task("testClasses")