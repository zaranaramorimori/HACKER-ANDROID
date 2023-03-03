plugins {
    id("com.teamzzong.hacker.feature")
    id("com.teamzzong.hacker.serialization")
    id("com.teamzzong.hacker.hilt")
}

android {
    namespace = "com.teamzzong.hacker.auth"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildFeatures {
        compose = true
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
    implementation(libs.bundles.androidx)
    implementation(libs.bundles.retrofit)
    implementation(libs.timber)
    implementation(libs.coil)
    implementation(libs.bundles.compose)
    debugImplementation(libs.compose.ui.tooling)

    implementation(platform(libs.firebase))
    implementation(libs.bundles.firebase)
    implementation(libs.inappupdate)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.espresso)
}