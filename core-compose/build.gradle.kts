plugins {
    id("com.teamzzong.hacker.feature")
}

android {
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
    namespace = "com.teamzzong.hacker.compose"
}

dependencies {
    implementation(project(":shared"))
    implementation(libs.bundles.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.espresso)
}