apply {
    from("gradle/projectDependencyGraph.gradle")
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.android.buid)
        classpath(libs.kotlin.gradle)
        classpath(libs.hilt.gradle)
        classpath(libs.google.oss.plugin)
        classpath(libs.google.services.plugin)
        classpath(libs.google.appdistribution.gradle)
        classpath(libs.google.crashlytics.gradle)
        classpath(libs.square.javapoet)
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}