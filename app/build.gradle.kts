plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
    id("com.google.android.gms.oss-licenses-plugin")
    id("com.google.gms.google-services")
    id("com.google.firebase.appdistribution")
    id("com.google.firebase.crashlytics")
}

android {
    compileSdk = Constants.compileSdk
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = Constants.packageName
        minSdk = Constants.minSdk
        targetSdk = Constants.targetSdk
        versionCode = Constants.versionCode
        versionName = Constants.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        getByName("debug") {
            keyAlias = "androiddebugkey"
            keyPassword = "android"
            storeFile = File("${project.rootDir.absolutePath}/keystore/debug.keystore")
            storePassword = "android"
        }
        create("release") {
            keyAlias = "hackerandroid"
            keyPassword = "hacker1234!"
            storeFile = File("${project.rootDir.absolutePath}/keystore/releasekey")
            storePassword = "hacker1234!"
        }
    }

    buildTypes {
        getByName("debug") {
            configure<com.google.firebase.appdistribution.gradle.AppDistributionExtension> {
                appId = "1:496905678269:android:a84b7af7d17001f614b6d4"
                serviceCredentialsFile =
                    file("../keystore/firebase_distribution_key.json").absolutePath
                artifactType = "APK"
                testers =
                    "l2hyunwoo@gmail.com, hansua990928@gmail.com, kkk5474096@naver.com, hansh0101@naver.com, tpgns7708@gmail.com"
            }
        }
        getByName("release") {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            configure<com.google.firebase.appdistribution.gradle.AppDistributionExtension> {
                appId = "1:496905678269:android:a84b7af7d17001f614b6d4"
                serviceCredentialsFile =
                    file("../keystore/firebase_distribution_key.json").absolutePath
                artifactType = "APK"
                testers =
                    "l2hyunwoo@gmail.com, hansua990928@gmail.com, kkk5474096@naver.com, hansh0101@naver.com, tpgns7708@gmail.com"
            }
        }
    }

    compileOptions {
        sourceCompatibility = Versions.javaVersion
        targetCompatibility = Versions.javaVersion
    }

    kotlinOptions {
        jvmTarget = Versions.jvmVersion
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidx.compose.plugin.get()
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
        compose = true
    }
    namespace = "com.teamzzong.hacker"
}

dependencies {
    // Project Dependency
    implementation(project(":core-compose"))
    implementation(project(":feature:auth"))
    implementation(project(":feature:notification"))
    implementation(project(":shared"))
    implementation(project(":core-ui"))
    implementation(project(":domain"))
    implementation(project(":data"))
    implementation(libs.bundles.kotlin)
    implementation(libs.bundles.androidx)
    implementation(libs.androidx.startup)
    implementation(libs.androidx.splash)
    implementation(libs.hilt)
    kapt(libs.hilt.kapt)
    implementation(libs.androidx.hilt.work)
    kapt(libs.androidx.hilt.work.compiler)
    implementation(libs.bundles.compose)
    debugImplementation(libs.compose.ui.tooling)
    implementation(libs.compose.glance)

    // Third-Party
    implementation(libs.coil)
    implementation(platform(libs.okhttp.bom))
    implementation(libs.bundles.okhttp)
    implementation(libs.retrofit)
    implementation(libs.retrofit.kotlin.serialization.converter)
    implementation(libs.bundles.retrofit)
    implementation(libs.timber)
    implementation(libs.oss.license)
    implementation(libs.kakao.login)
    implementation(libs.skydoves.balloon)
    implementation(libs.skydoves.progressview)
    implementation(libs.lottie)
    implementation(libs.processphoenix)
    implementation(libs.pulltorefresh)
    implementation(libs.google.admob)
    implementation(libs.material)

    // Firebase Dependency
    implementation(platform(libs.firebase))
    implementation(libs.bundles.firebase)
    debugImplementation(libs.bundles.flipper)
    debugImplementation(libs.flipper.network) {
        exclude(group = "com.squareup.okhttp3", module = "okhttp")
    }

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.espresso)
}

kapt {
    correctErrorTypes = true
}

hilt {
    enableAggregatingTask = true
}
