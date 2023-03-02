plugins {
    kotlin("android")
    kotlin("plugin.serialization") version "1.8.0"
    kotlin("kapt")
    id("com.android.library")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.teamzzong.hacker.auth"
    compileSdk = Constants.compileSdk

    defaultConfig {
        minSdk = Constants.minSdk
        targetSdk = Constants.targetSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
        compose = true
    }

    compileOptions {
        sourceCompatibility = Versions.javaVersion
        targetCompatibility = Versions.javaVersion
    }

    kotlinOptions {
        jvmTarget = Versions.jvmVersion
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidx.compose.plugin.get()
    }
}

dependencies {
    implementation(project(":core-compose"))
    implementation(project(":core-ui"))
    implementation(project(":shared"))
    implementation(project(":domain"))
    implementation(libs.kotlin)
    implementation(libs.kotlin.serialization.json)
    implementation(libs.material)
    implementation(libs.bundles.androidx)
    implementation(libs.bundles.retrofit)
    implementation(libs.timber)
    implementation(libs.coil)
    implementation(libs.bundles.compose)
    debugImplementation(libs.compose.ui.tooling)

    implementation(platform(libs.firebase))
    implementation(libs.bundles.firebase)
    implementation(libs.inappupdate)

    implementation(libs.hilt)
    kapt(libs.hilt.kapt)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.espresso)
}