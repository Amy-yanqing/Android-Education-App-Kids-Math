plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    kotlin("kapt")
}


android {
    namespace = "au.edu.jcu.kidsmath"
    compileSdk = 34

    defaultConfig {
        applicationId = "au.edu.jcu.kidsmath"
        minSdk = 24
        targetSdk = 34
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        dataBinding = true
    }
}
dependencies {
    // Coil
    implementation("io.coil-kt:coil:1.1.1")
    // Moshi
    implementation("com.squareup.moshi:moshi-kotlin:1.13.0")
    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    // Retrofit with Moshi Converter
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.picasso:picasso:2.71828")

    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.junit.ktx)
    implementation(libs.androidx.monitor)
    kapt(libs.androidx.room.compiler)
    testImplementation(libs.androidx.room.testing)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.recyclerview)

    // Unit testing dependencies
    testImplementation(libs.junit) // JUnit for unit tests
    testImplementation("org.mockito:mockito-core:4.8.0") // Mockito for mocking
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1") // Coroutines test library

    // Android Instrumented testing dependencies
    androidTestImplementation(libs.androidx.junit) // JUnit for Android tests
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.test:core-ktx:1.5.0") // Core testing library for Android
    androidTestImplementation("androidx.test:rules:1.5.0") // Rules for Android tests
    androidTestImplementation("androidx.test:runner:1.5.0") // Test runner for Android

    // Room testing dependency
    testImplementation(libs.androidx.room.testing) // Room testing library
}

