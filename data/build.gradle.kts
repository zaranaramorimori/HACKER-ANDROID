plugins {
    id("com.teamzzong.hacker.feature")
    id("com.teamzzong.hacker.serialization")
    id("com.teamzzong.hacker.hilt")
}

android {
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    namespace = "com.teamzzong.hacker.data"
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":shared"))
    implementation(libs.bundles.kotlin)
    implementation(libs.androidx.security)
    implementation(libs.bundles.retrofit)
    implementation(libs.kakao.login)
    implementation(libs.timber)
    implementation(libs.processphoenix)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.espresso)
}