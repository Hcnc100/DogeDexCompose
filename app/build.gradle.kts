plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
    id("kotlin-parcelize")
    id("com.google.devtools.ksp")
}


android {
    compileSdk = 34

    defaultConfig {
        applicationId = "com.d34th.nullpointer.dogedex"
        minSdk = 21
        targetSdk = 32
        versionCode = 2
        versionName = "2.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }


    testOptions {
        execution = "ANDROIDX_TEST_ORCHESTRATOR"
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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


    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = freeCompilerArgs + listOf(
                "-opt-in=androidx.compose.animation.ExperimentalAnimationApi",
                "-opt-in=androidx.compose.material.ExperimentalMaterialApi",
            )
        }
    }

    applicationVariants.all {
        addJavaSourceFoldersToModel(
            File(buildDir, "generated/ksp/$name/kotlin")
        )
    }

    namespace ="com.d34th.nullpointer.dogedex"
}

dependencies {
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.compose.ui:ui:1.6.0")
    implementation("androidx.compose.material:material:1.6.0")
    implementation("androidx.compose.ui:ui-tooling-preview:1.6.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.5.1")
    implementation("androidx.activity:activity-compose:1.5.1")
    implementation("androidx.test.ext:junit-ktx:1.1.3")
    implementation("androidx.test:runner:1.4.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.6.0")
    debugImplementation("androidx.compose.ui:ui-tooling:1.6.0")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.6.0")

    // * coil
    implementation("io.coil-kt:coil-compose:2.1.0")

    // *lottie compose
    implementation("com.airbnb.android:lottie-compose:5.1.1")

    // * timber
    implementation("com.orhanobut:logger:2.2.0")
    implementation("com.jakewharton.timber:timber:4.7.1")

    // * hilt
    val daggerHiltVersion = "2.50"
    implementation("com.google.dagger:hilt-android:${daggerHiltVersion}")
    kapt("com.google.dagger:hilt-compiler:${daggerHiltVersion}")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    // ? hilt test
    testImplementation("com.google.dagger:hilt-android-testing:${daggerHiltVersion}")
    androidTestImplementation("com.google.dagger:hilt-android-testing:${daggerHiltVersion}")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:${daggerHiltVersion}")

    // * room
    val roomVersion = "2.6.1"
    implementation("androidx.room:room-runtime:${roomVersion}")
    kapt("androidx.room:room-compiler:${roomVersion}")
    implementation("androidx.room:room-ktx:${roomVersion}")
    testImplementation("androidx.room:room-testing:${roomVersion}")

    // * save state
    implementation("androidx.savedstate:savedstate-ktx:1.2.0")

    // * image compressor
    implementation("com.github.Shouheng88:compressor:1.6.0")

    // * splash screen
    implementation("androidx.core:core-splashscreen:1.0.0")

    // * shimmer effect
    implementation("com.valentinilk.shimmer:compose-shimmer:1.0.3")

    // * navigation
    val destinationsVersion = "1.10.0"
    implementation("io.github.raamcosta.compose-destinations:core:$destinationsVersion")
    ksp("io.github.raamcosta.compose-destinations:ksp:$destinationsVersion")

    // * data store
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    // * gson
    implementation("com.google.code.gson:gson:2.8.9")

    // * Retrofit
    val retrofitVersion = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:${retrofitVersion}")
    implementation("com.squareup.retrofit2:converter-gson:${retrofitVersion}")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")
    implementation("com.squareup.retrofit2:converter-scalars:${retrofitVersion}")
    // ? retrofit test
    testImplementation("com.squareup.okhttp3:mockwebserver:4.8.1")

    // * camera
    implementation("androidx.camera:camera-camera2:1.1.0")
    implementation("androidx.camera:camera-lifecycle:1.1.0")
    implementation("androidx.camera:camera-view:1.1.0")

    // * permission
    implementation("com.google.accompanist:accompanist-permissions:0.26.1-alpha")

    // * Tensorflow
    implementation("org.tensorflow:tensorflow-lite:2.9.0")
    implementation("org.tensorflow:tensorflow-lite-gpu:2.8.0")
    implementation("org.tensorflow:tensorflow-lite-support:0.4.2")

    // ? android test
    testImplementation("androidx.arch.core:core-testing:2.1.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
    androidTestImplementation("androidx.test:rules:1.4.0")
    androidTestUtil("androidx.test:orchestrator:1.4.1")

    implementation("com.google.accompanist:accompanist-swiperefresh:0.25.1")
}

kapt {
    correctErrorTypes = true
}
