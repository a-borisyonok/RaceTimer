plugins {
    id ("com.android.application")
    id ("kotlin-android")
    id ("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
}

android {
    compileSdkVersion (31)
    buildToolsVersion  ("30.0.3")

    defaultConfig {
        applicationId ("com.seka.racetimer")
        minSdkVersion  (26)
        targetSdkVersion (31)
        versionCode  (1)
        versionName ("1.0")

        testInstrumentationRunner ("androidx.test.runner.AndroidJUnitRunner")

    }

    buildTypes {
        getByName("release")  {
            minifyEnabled ( false)
            proguardFiles (getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget  = ("1.8")
    }
}
kapt {
    arguments {
        // Make Hilt share the same definition of Components in tests instead of
        // creating a new set of Components per test class.
        arg("dagger.hilt.shareTestComponents", "true")
    }
}
dependencies {

    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.5.31")
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.0")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.2")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.4.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0")


    implementation("androidx.navigation:navigation-fragment-ktx:2.3.5")

    implementation("androidx.navigation:navigation-ui-ktx:2.3.5")


    implementation("androidx.room:room-runtime:2.3.0")

    implementation("androidx.room:room-ktx:2.3.0")

    kapt("androidx.room:room-compiler:2.3.0")


    implementation("com.google.dagger:hilt-android:2.40.1")

    kapt("com.google.dagger:hilt-compiler:2.40.1")


    testImplementation("junit:junit:4.13.2")

    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0-RC")


    testImplementation("androidx.test:core:1.4.0")

    testImplementation("org.mockito:mockito-inline:3.10.0")

    testImplementation("org.mockito:mockito-core:4.0.0")

}