plugins {
    id("com.android.application")
    id("kotlin-parcelize")
    kotlin("android")
    kotlin("kapt")
    kotlin("plugin.serialization") version "1.6.10"
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
}

android {
    defaultConfig {
        applicationId = "org.cornfarmer"
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true

            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        dataBinding = true
    }

    namespace = "org.cornfarmer"
}

dependencies {
    implementation(libs.bundles.androidx)
    implementation(libs.bundles.kotlin)
    implementation(libs.bundles.dagger)
    implementation(libs.retrofit)
    implementation(libs.okhttp)
    implementation(libs.coil.core)
    implementation(libs.okhttp.bom)
    implementation(libs.okhttp.loggingInterceptor)
    implementation(libs.timber)
    implementation(libs.leakCanary)
    implementation(libs.lottie)
    implementation(libs.kotlin.serialization.converter)
    implementation(libs.junit)
    implementation(libs.circleimage)
    implementation(libs.glide)
    implementation(libs.gson)
    kapt(libs.bundles.compiler)
}