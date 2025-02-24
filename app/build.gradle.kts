plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "ru.kpfu.itis.paramonov.videoapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "ru.kpfu.itis.paramonov.videoapp"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":core:core"))
    implementation(project(":core:network"))
    implementation(project(":core:navigation"))
    implementation(project(":core:database"))
    implementation(project(":feature:videos:api"))
    implementation(project(":feature:videos:impl"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    implementation(libs.bundles.koin)
    implementation(libs.bundles.compose)
    implementation(libs.bundles.mvi.core)
    implementation(libs.bundles.coil)
    implementation(libs.androidx.compose.navigation)

    implementation(libs.viewmodel.ktx)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}