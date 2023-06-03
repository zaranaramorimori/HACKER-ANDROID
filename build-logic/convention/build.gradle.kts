plugins {
    `kotlin-dsl`
}

group = "com.teamzzong.hacker.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.android.build)
    compileOnly(libs.kotlin.gradle)
}

gradlePlugin {
    plugins {
        create("android-application") {
            id = "com.teamzzong.hacker.application"
            implementationClass = "com.teamzzong.hacker.plugin.AndroidApplicationPlugin"
        }
        create("android-feature") {
            id = "com.teamzzong.hacker.feature"
            implementationClass = "com.teamzzong.hacker.plugin.AndroidFeaturePlugin"
        }
        create("android-kotlin") {
            id = "com.teamzzong.hacker.kotlin"
            implementationClass = "com.teamzzong.hacker.plugin.AndroidKotlinPlugin"
        }
        create("android-hilt") {
            id = "com.teamzzong.hacker.hilt"
            implementationClass = "com.teamzzong.hacker.plugin.AndroidHiltPlugin"
        }
        create("kotlin-serialization") {
            id = "com.teamzzong.hacker.serialization"
            implementationClass = "com.teamzzong.hacker.plugin.KotlinSerializationPlugin"
        }
    }
}
