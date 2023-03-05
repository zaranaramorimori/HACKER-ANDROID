import java.util.Properties

plugins {
    id("com.teamzzong.hacker.application")
    id("com.teamzzong.hacker.hilt")
    alias(libs.plugins.google.services)
    alias(libs.plugins.google.firebase.crashlytics)
    alias(libs.plugins.google.secret.plugin)
    alias(libs.plugins.google.firebase.appdistribution)
    id("com.google.android.gms.oss-licenses-plugin")
}

android {
    val properties = Properties().apply {
        load(file("../local.properties").inputStream())
    }

    buildToolsVersion = "30.0.3"

    defaultConfig {
        val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

        applicationId = "com.teamzzong.hacker"
        versionName = libs.findVersion("appVersion").get().requiredVersion
        versionCode = checkNotNull(libs.findVersion("versionCode").get().requiredVersion).toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        getByName("debug") {
            keyAlias = "${properties["DEBUG_KEY_ALIAS"]}"
            keyPassword = "${properties["DEBUG_KEY_PASSWORD"]}"
            storeFile = File("${project.rootDir.absolutePath}/keystore/debug.keystore")
            storePassword = "${properties["DEBUG_KEY_PASSWORD"]}"
        }
        create("release") {
            keyAlias = "\"${properties["RELEASE_KEY_ALIAS"]}\""
            keyPassword = "\"${properties["RELEASE_KEY_PASSWORD"]}\""
            storeFile = File("${project.rootDir.absolutePath}/keystore/releasekey")
            storePassword = "\"${properties["RELEASE_KEY_PASSWORD"]}\""
        }
    }

    buildTypes {
        getByName("debug") {
            configure<com.google.firebase.appdistribution.gradle.AppDistributionExtension> {
                appId = "${properties["APP_DISTRIBUTION_ID"]}"
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
                appId = "\"${properties["APP_DISTRIBUTION_ID"]}\""
                serviceCredentialsFile =
                    file("../keystore/firebase_distribution_key.json").absolutePath
                artifactType = "APK"
                testers =
                    "l2hyunwoo@gmail.com, hansua990928@gmail.com, kkk5474096@naver.com, hansh0101@naver.com, tpgns7708@gmail.com"
            }
        }
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidx.compose.plugin.get()
    }
    buildFeatures {
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
