apply {
    from("gradle/projectDependencyGraph.gradle")
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.android.build)
        classpath(libs.kotlin.gradle)
        classpath(libs.hilt.gradle)
        classpath(libs.google.oss.plugin)
        classpath(libs.google.services.plugin)
        classpath(libs.google.appdistribution.gradle)
        classpath(libs.google.crashlytics.gradle)
        classpath(libs.square.javapoet)
        classpath(libs.google.secrets.plugin)
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.dagger.hilt) apply false
    alias(libs.plugins.kotlinx.serialization) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.google.firebase.crashlytics) apply false
    alias(libs.plugins.google.secret.plugin) apply false
    alias(libs.plugins.google.firebase.appdistribution) apply false
}


tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}