plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    // Kotlin serialization plugin for type-safe routes and navigation arguments
    kotlin("plugin.serialization") version "2.0.0"

    // Hilt plugin for Dependency Injection
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.android.hilt)

    //firebase
    // alias(libs.plugins.google.gms.google.services)


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
            buildConfigField("String", "baseUrl", "\"http://10.57.250.250:3000/\"")
            buildConfigField(
                "String",
                "delhiveryBaseUrl",
                "\"https://staging-express.delhivery.com/c/api/\""
            )
            buildConfigField("String", "pinBaseUrl", "\"https://api.postalpincode.in/\"")

        }

        debug {
            buildConfigField(
                "String",
                "delhiveryBaseUrl",
                "\"https://staging-express.delhivery.com/c/api/\""
            )
            buildConfigField("String", "baseUrl", "\"http://10.57.250.250:3000/\"")
            buildConfigField("String", "pinBaseUrl", "\"https://api.postalpincode.in/\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
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
    implementation(libs.androidx.appcompat)
//    implementation(libs.identity.jvm)

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
    implementation(libs.logging.interceptor)

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // Room dependencies (uses KSP/KAPT)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)

    // Firebase
//    implementation(platform(libs.firebase.bom))
//    implementation(libs.firebase.auth)
//    implementation(libs.firebase.auth)

    // Hilt dependencies (uses KAPT)
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    // annotation processor
    kapt(libs.hilt.android.compiler)


    implementation(libs.kotlinx.datetime)

    implementation(libs.androidx.datastore.preferences)

    testImplementation(libs.mockwebserver)

    // for encryption and decryption
    implementation(libs.androidx.security.crypto)
    implementation(libs.android.pdf.viewer)
//    implementation ("com.github.barteksc:android-pdf-viewer:2.8.2")

//    implementation ("androidx.security:security-crypto:1.1.0-alpha06")
//    implementation ("com.github.barteksc:android-pdf-viewer:3.2.0-beta.1")
//    implementation ("com.github.barteksc:android-pdf-viewer:2.8.2")

    // WorkManager for background tasks
//    implementation(libs.androidx.work.runtime.ktx)
    implementation("androidx.work:work-runtime-ktx:2.10.0")

    implementation("phonepe.intentsdk.android.release:IntentSDK:4.0.0")
//    implementation("phonepe.intentsdk.android.release:IntentSDK:2.4.3")

//    implementation(libs.accompanist.systemuicontroller)   //deprecated

}
// Allow references to generated code
kapt {
    correctErrorTypes = true
}
