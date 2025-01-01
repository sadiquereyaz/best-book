plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    // Kotlin serialization plugin for type-safe routes and navigation arguments
    kotlin("plugin.serialization") version "2.0.21"
    //ksp
   // alias(libs.plugins.ksp)

    // Hilt plugin for Dependency Injection
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.android.hilt)
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

    // Firebase
//    implementation(libs.firebase.auth.ktx)
//    implementation(libs.firebase.database)
//    implementation(platform(libs.firebase.bom))
//    implementation(libs.firebase.analytics)
//    implementation(libs.firebase.auth)

    // Compose and UI Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Coil for image loading
    //implementation(libs.coil.compose)

    // JSON Serialization
    implementation(libs.kotlinx.serialization.json)

    // Retrofit for network calls
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // OkHttp for rendering PDFs from a URL
    // implementation(libs.okhttp)

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // Hilt dependencies (uses KAPT)
    //hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)


// Room dependencies (uses KSP)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
  //  ksp(libs.androidx.room.compiler)
}
// Allow references to generated code
kapt {
    correctErrorTypes = true
}