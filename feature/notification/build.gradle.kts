plugins {
    kotlin("android")
    kotlin("kapt")
    id("com.android.library")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.teamzzong.hacker.notification"
    compileSdk = Constants.compileSdk

    defaultConfig {
        minSdk = Constants.minSdk
        targetSdk = Constants.targetSdk

        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = Versions.javaVersion
        targetCompatibility = Versions.javaVersion
    }

    kotlinOptions {
        jvmTarget = Versions.jvmVersion
    }
}

dependencies {
    implementation(project(":core-ui"))
    implementation(project(":shared"))
    implementation(project(":domain"))
    implementation(libs.kotlin)
    implementation(libs.kotlin.datetime)
    implementation(libs.material)
    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.lifecycle.livedata)
    implementation(libs.timber)
    implementation(libs.coil)
    implementation(platform(libs.firebase))
    implementation(libs.firebase.messaging)
    implementation(libs.hilt)
    kapt(libs.hilt.kapt)
}