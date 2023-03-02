plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
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

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
    namespace = "com.teamzzong.hacker.ui"
}

dependencies {
    implementation(project(":shared"))
    implementation(libs.kotlin)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.material)

    implementation(libs.hilt)
    kapt(libs.hilt.kapt)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.espresso)
}