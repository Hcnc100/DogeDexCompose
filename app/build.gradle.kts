import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
    id("com.google.devtools.ksp")
    id("org.jetbrains.kotlin.plugin.serialization")
}

// * DEFINE CONSTANTS

val storeFileProperty: String = gradleLocalProperties(rootDir).getProperty("STORE_FILE")
val storePasswordProperty: String = gradleLocalProperties(rootDir).getProperty("STORE_PASSWORD")
val keyAliasProperty: String = gradleLocalProperties(rootDir).getProperty("KEY_ALIAS")
val keyPasswordProperty: String = gradleLocalProperties(rootDir).getProperty("KEY_PASSWORD")


android {
    compileSdk = 34

    signingConfigs {
        create("release") {
            storeFile = file(storeFileProperty)
            storePassword = storePasswordProperty
            keyAlias = keyAliasProperty
            keyPassword = keyPasswordProperty
        }
    }

    defaultConfig {
        applicationId = "com.nullpointer.dogedex"
        minSdk = 23
        targetSdk = 34
        versionCode = 4
        versionName = "3.0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf(
                    "room.schemaLocation" to "$projectDir/schemas",
                    "room.incremental" to "true"
                )
            }
        }

        multiDexEnabled = true
    }


    testOptions {
        execution = "ANDROIDX_TEST_ORCHESTRATOR"
    }
    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
                "retrofit2.pro"
            )
            signingConfig = signingConfigs.getByName("release")
        }

        create("profile") {
            isMinifyEnabled = true
            isDebuggable = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
                "proguard-rules-profile.pro",
                "retrofit2.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
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

    namespace = "com.nullpointer.dogedex"
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.compose.ui:ui:1.6.0")
    implementation("androidx.compose.material:material:1.6.0")
    implementation("androidx.compose.ui:ui-tooling-preview:1.6.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.test.ext:junit-ktx:1.1.5")
    implementation("androidx.test:runner:1.5.2")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.6.0")
    debugImplementation("androidx.compose.ui:ui-tooling:1.6.0")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.6.0")

    // * coil
    implementation("io.coil-kt:coil-compose:2.5.0")

    // *lottie compose
    implementation("com.airbnb.android:lottie-compose:6.3.0")

    // * timber
    implementation("com.orhanobut:logger:2.2.0")
    implementation("com.jakewharton.timber:timber:5.0.1")

    // * hilt
    val daggerHiltVersion = "2.50"
    implementation("com.google.dagger:hilt-android:${daggerHiltVersion}")
    kapt("com.google.dagger:hilt-compiler:${daggerHiltVersion}")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    // ? hilt test
    testImplementation("com.google.dagger:hilt-android-testing:${daggerHiltVersion}")
    androidTestImplementation("com.google.dagger:hilt-android-testing:${daggerHiltVersion}")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:${daggerHiltVersion}")

    // * room
    val roomVersion = "2.6.1"
    implementation("androidx.room:room-runtime:${roomVersion}")
    ksp("androidx.room:room-compiler:${roomVersion}")
    implementation("androidx.room:room-ktx:${roomVersion}")
    testImplementation("androidx.room:room-testing:${roomVersion}")

    // * save state
    implementation("androidx.savedstate:savedstate-ktx:1.2.1")

    // * image compressor
    implementation("com.github.Shouheng88:compressor:1.6.0")

    // * splash screen
    implementation("androidx.core:core-splashscreen:1.0.1")

    // * shimmer effect
    implementation("com.valentinilk.shimmer:compose-shimmer:1.2.0")

    // * navigation
    val destinationsVersion = "1.10.0"
    implementation("io.github.raamcosta.compose-destinations:core:$destinationsVersion")
    ksp("io.github.raamcosta.compose-destinations:ksp:$destinationsVersion")

    // * data store
    implementation("io.github.osipxd:security-crypto-datastore-preferences:1.0.0-beta01")
    implementation("com.google.errorprone:error_prone_annotations:2.15.0")


    // * Retrofit
    val retrofitVersion = "2.9.0"
    implementation("com.squareup.retrofit2:retrofit:${retrofitVersion}")
    implementation("com.squareup.retrofit2:converter-gson:${retrofitVersion}")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")
    implementation("com.squareup.retrofit2:converter-scalars:${retrofitVersion}")
    // ? retrofit test
    testImplementation("com.squareup.okhttp3:mockwebserver:4.8.1")

    // * camera
    implementation("androidx.camera:camera-extensions:1.3.1")
    implementation("androidx.camera:camera-lifecycle:1.3.1")
    implementation("androidx.camera:camera-view:1.3.1")

    // * permission
    implementation("com.google.accompanist:accompanist-permissions:0.28.0")

    // * Tensorflow
    implementation("org.tensorflow:tensorflow-lite:2.9.0")
    implementation("org.tensorflow:tensorflow-lite-gpu:2.8.0")
    implementation("org.tensorflow:tensorflow-lite-support:0.4.2")

    // ? android test
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    androidTestImplementation("androidx.test:rules:1.5.0")
    androidTestUtil("androidx.test:orchestrator:1.4.2")

    implementation("com.google.accompanist:accompanist-swiperefresh:0.25.1")

    // * kotlin serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.2")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")

    implementation("org.jetbrains.kotlin:kotlin-reflect:1.9.10")

}

kapt {
    correctErrorTypes = true
}
