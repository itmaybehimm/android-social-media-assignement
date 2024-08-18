plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.socialmedia"

    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.socialmedia"
        minSdk = 22
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(17) // Ensure this matches your project's Java requirements
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
}

dependencies {
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.foundation:foundation-layout:1.4.0")
    implementation("androidx.navigation:navigation-compose:2.7.0")
    implementation("androidx.compose.ui:ui:1.4.0")
    implementation("androidx.compose.foundation:foundation:1.4.0")
    implementation("androidx.navigation:navigation-compose:2.7.0")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.material:material")
    implementation("androidx.compose.foundation:foundation:1.5.0")
    implementation("androidx.compose.material:material-icons-extended:1.4.3")
    implementation("androidx.compose.material3:material3:1.1.0")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.runtime:runtime-livedata")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.activity:activity-compose:1.8.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.5.1")
    debugImplementation("androidx.compose.ui:ui-tooling:1.5.1")
}