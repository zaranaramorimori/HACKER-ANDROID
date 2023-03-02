plugins {
    id("java-library")
    kotlin("jvm")
    kotlin("kapt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    implementation(libs.bundles.kotlin)
    implementation(libs.hilt.core)
    kapt(libs.hilt.kapt)
}