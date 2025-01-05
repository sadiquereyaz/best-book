plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    // Kotlin serialization plugin for type-safe routes and navigation arguments
    kotlin("plugin.serialization") version "2.0.21"

    // Hilt plugin for Dependency Injection
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.android.hilt)

    //firebase
    alias(libs.plugins.google.gms.google.services)



}

android {
    namespace = "com.nabssam.bestbook"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.nabssam.bestbook"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    // AndroidX core dependencies
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.firebase.auth)
    // Compose and UI Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // for image loading from internet
    implementation(libs.coil.compose)
    implementation(libs.okhttp)

    // JSON Serialization
    implementation(libs.kotlinx.serialization.json)

    // Retrofit for network calls
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // Room dependencies (uses KSP)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.auth)

    // Hilt dependencies (uses KAPT)
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    // annotation processor
    kapt(libs.hilt.android.compiler)

}
// Allow references to generated code
kapt {
    correctErrorTypes = true
}
