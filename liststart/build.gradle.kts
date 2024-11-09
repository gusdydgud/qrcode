plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.liststart"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.liststart"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        multiDexEnabled= true
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

    viewBinding.isEnabled = true
}

dependencies {
    // Location 관련 프로젝트 의존성
    implementation("org.locationtech.proj4j:proj4j:1.1.0")

    // 코루틴 지원
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0")

    // 기존에 필요했던 라이브러리들
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.camera.lifecycle)

    // Google Play Services 제거, 필요한 경우 특정 모듈만 포함하도록 설정
    // 예시: implementation("com.google.android.gms:play-services-location:20.0.0") 또는 "play-services-maps" 제외

    // Retrofit과 Gson
    implementation(libs.retrofit)
    implementation(libs.converter.gson)

    // Android Lifecycle 컴포넌트
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.activity.ktx)

    // Barcode Scanning (Google ML Kit)
    implementation("com.google.mlkit:barcode-scanning:17.0.3")
    implementation ("androidx.camera:camera-camera2:1.0.2")
    implementation ("androidx.camera:camera-lifecycle:1.0.2")
    implementation ("androidx.camera:camera-view:1.0.0-alpha28")
    implementation ("androidx.camera:camera-extensions:1.0.0-alpha28")
    // 테스트 라이브러리
    testImplementation(libs.mockwebserver)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
