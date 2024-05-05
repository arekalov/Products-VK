plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs.kotlin")
}


android {
    namespace = "com.arekalov.presentation"
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
        viewBinding = true
    }
}

dependencies {
//    Paging
        implementation("androidx.paging:paging-runtime:3.2.1") {

//        ssp sdp
        implementation("com.intuit.sdp:sdp-android:1.1.0")
        implementation("com.intuit.ssp:ssp-android:1.1.0")

//        data module
        implementation(project(":data"))

//        glide
        implementation("com.github.bumptech.glide:glide:4.16.0")

//    pagination
        implementation("androidx.paging:paging-runtime:3.2.1")

//        carousel
        implementation("com.google.android.material:material:1.9.0")


        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")
        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.appcompat)
        implementation(libs.material)
        implementation(libs.androidx.navigation.fragment.ktx)
        implementation(libs.androidx.navigation.ui.ktx)
        testImplementation(libs.junit)
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)
    }
}