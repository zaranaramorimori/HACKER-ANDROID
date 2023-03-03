plugins {
    id("com.teamzzong.hacker.feature")
    id("com.teamzzong.hacker.hilt")
}

android {
    namespace = "com.teamzzong.hacker.notification"

    defaultConfig {
        consumerProguardFiles("consumer-rules.pro")
    }
}

dependencies {
    implementation(project(":core-ui"))
    implementation(project(":shared"))
    implementation(project(":domain"))
    implementation(libs.kotlin.datetime)
    implementation(libs.androidx.fragment)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.lifecycle.livedata)
    implementation(libs.timber)
    implementation(libs.coil)
    implementation(platform(libs.firebase))
    implementation(libs.firebase.messaging)
}