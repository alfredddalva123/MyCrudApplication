plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.mycrudapplication"
    compileSdk = 34 // Keep at 34 to match AGP 8.4.2

    defaultConfig {
        applicationId = "com.example.mycrudapplication"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    // ... rest of your code remains the same
}

dependencies {
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}