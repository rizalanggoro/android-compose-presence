// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false

    // dagger hilt
    id("com.google.dagger.hilt.android") version "2.52" apply false

    // compose compiler
    alias(libs.plugins.compose.compiler) apply false
}