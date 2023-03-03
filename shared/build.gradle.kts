plugins {
    id("com.teamzzong.hacker.feature")
    id("com.teamzzong.hacker.serialization")
}

android {
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    namespace = "com.teamzzong.hacker.shared"
}

dependencies {
    implementation(libs.kotlin.datetime)
    implementation(libs.timber)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.espresso)
}