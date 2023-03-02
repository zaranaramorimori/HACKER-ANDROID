plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    alias(libs.plugins.kotlinx.serialization)
}

android {
    compileSdk = Constants.compileSdk

    defaultConfig {
        minSdk = Constants.minSdk
        targetSdk = Constants.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = Versions.javaVersion
        targetCompatibility = Versions.javaVersion
    }
    kotlinOptions {
        jvmTarget = Versions.jvmVersion
    }
    namespace = "com.teamzzong.hacker.data"
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":shared"))
    implementation(libs.bundles.kotlin)
    implementation(libs.hilt)
    kapt(libs.hilt.kapt)
    implementation(libs.androidx.security)
    implementation(libs.androidx.core)
    implementation(libs.bundles.retrofit)
    implementation(libs.kakao.login)
    implementation(libs.timber)
    implementation(libs.processphoenix)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.espresso)
}