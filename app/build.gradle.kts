plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.example.jetpackcomposeanime"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.jetpackcomposeanime"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.inline)

    implementation(libs.androidx.paging.compose)
    implementation(libs.paging.runtime)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.accompanist.systemuicontroller)
    implementation(libs.coil.compose)

    implementation(libs.material3)
    implementation(libs.ui)
    implementation(libs.androidx.material)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.accompanist.placeholder.material)

    implementation(platform("com.squareup.okhttp3:okhttp-bom:4.12.0"))
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

    implementation(libs.android.database.sqlcipher)
    implementation(libs.androidx.sqlite.ktx)

    implementation(libs.converter.gson)
    implementation(libs.retrofit2.retrofit)

    implementation(libs.androidx.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.androidx.room.paging)
    testImplementation(libs.junit.v412)
    kapt(libs.androidx.room.compiler)
    annotationProcessor(libs.androidx.room.compiler)

    implementation(libs.hilt.android)
    kapt(libs.dagger.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}