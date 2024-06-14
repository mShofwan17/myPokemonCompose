plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.kotlinKapt)
    alias(libs.plugins.hiltAndroid)
}

android {
    namespace = "me.project.network"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            val baseUrl = "https://pokeapi.co/"
            buildConfigField("String", "BASE_URL", "\"$baseUrl\"")
            buildConfigField("String", "BASE_V2", "\"$baseUrl\" + \"api/v2//\"")
        }
        debug {
            val baseUrl = "https://pokeapi.co/"
            buildConfigField("String", "BASE_URL", "\"$baseUrl\"")
            buildConfigField("String", "BASE_V2", "\"$baseUrl\" + \"api/v2//\"")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // Retrofit x GSon
    implementation(libs.retrofit2)
    implementation(libs.retrofit2.converter.gson)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit2.converters)
    implementation(libs.gson)

    // Kotlin
    implementation(libs.corotines.core)
    implementation(libs.corotines.android)
}