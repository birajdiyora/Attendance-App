plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.devtools.ksp") version "1.8.21-1.0.11"
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.attendance"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.attendance"
        minSdk = 23
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.7"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
//    implementation ("com.maxkeppeler.sheets-compose-dialogs:core:1.2.1")
//    implementation ("com.maxkeppeler.sheets-compose-dialogs:calender:1.2.1")
    implementation ("androidx.compose.material3:material3:1.2.0-beta02")

    //Room
    implementation("androidx.room:room-runtime:${rootProject.extra["room_version"]}")
    implementation("androidx.core:core-ktx:1.10.1")
    implementation("androidx.room:room-ktx:${rootProject.extra["room_version"]}")
    ksp("androidx.room:room-compiler:${rootProject.extra["room_version"]}")
    kapt( "android.arch.persistence.room:compiler:1.1.1")

    //dagger hilt
    implementation("com.google.dagger:hilt-android:2.44.2")
    kapt("com.google.dagger:hilt-android-compiler:2.44.2")
    implementation ("androidx.hilt:hilt-navigation-compose:1.0.0")

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
    implementation("androidx.navigation:navigation-compose:2.6.0")

    implementation ("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
    implementation ("androidx.datastore:datastore-preferences-rxjava2:1.0.0")
    implementation ("androidx.datastore:datastore-preferences-rxjava3:1.0.0")

    //splash
    implementation ("androidx.core:core-splashscreen:1.0.1")
}