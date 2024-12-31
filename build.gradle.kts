// Top-level build file where you can add configuration options common to all sub-projects/modules.
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false

    // KSP for Room
    //id("com.google.devtools.ksp") version "1.9.10-1.0.13" apply false
    alias(libs.plugins.ksp) apply false

    // dagger-hilt
    //id("com.google.dagger.hilt.android") version "2.51.1" apply false
    alias(libs.plugins.hilt) apply false
}