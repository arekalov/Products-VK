plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-kapt")
}

android {
    namespace = "com.arekalov.productsvk"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.arekalov.productsvk"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }

    buildFeatures {
        viewBinding = true
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
}

dependencies {
    //    Paging
    implementation(libs.androidx.paging.runtime)

//        ssp sdp
    implementation(libs.sdp.android)
    implementation(libs.ssp.android)

//        glide
    implementation(libs.glide)

//    pagination
    implementation(libs.androidx.paging.runtime.ktx)

//        carousel
    implementation(libs.material.v190)

//  coroutines
    implementation(libs.kotlinx.coroutines.android)

//    dagger
    implementation(libs.dagger)
    kapt(libs.dagger.compiler)

    //    Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    testImplementation(libs.truth)
    androidTestImplementation(libs.truth)

    implementation(project(":data"))
}