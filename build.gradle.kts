// Top-level build file where you can add configuration options common to all sub-projects/modules.
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false

    // KSP for Room
   // alias(libs.plugins.ksp) apply false

    // dagger-hilt
    alias(libs.plugins.android.hilt) apply false
}