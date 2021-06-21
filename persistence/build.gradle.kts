plugins {
    id ("com.android.library")
    kotlin("android")
    id  ("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
    id ("kotlinx-serialization")
}

android {
    compileSdkVersion(Sdk.COMPILE_SDK_VERSION)
    buildToolsVersion("30.0.3")

    defaultConfig {
        minSdkVersion(Sdk.MIN_SDK_VERSION)
        targetSdkVersion(Sdk.TARGET_SDK_VERSION)
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }


    buildTypes {

        getByName("release") {
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
}

dependencies {


    implementation (Deps.KOTLIN_STLIB)
    //dipendencies
    implementation(Deps.coreKtx)
    //DIPENDENCY INJECTION
    //dagger
    implementation(DI.DAGGER_DEP)
    kapt(DI.DAGGER_ANNOTATION)
    // hilt
    implementation(DI.HILT_DEP)
    kapt(DI.HILT_KAPT)
    kaptAndroidTest(DI.HILT_ANDROID_KAPT_TEST)
    kaptTest(DI.HILT_KAPT_TEST)
    //Room
    implementation(Room.ROOM)
    kapt(Room.ROOM_COMPILER)
    kapt(Room.ROOM_COMMONS)
    implementation(Room.ROOM_RTX)
    androidTestImplementation(Room.ROOM_TESTING)





    // Coroutines
    implementation(Kotlin.COROUTINES)
    implementation(Kotlin.COROUTINES_ANDROID)

    //TESTING
    testImplementation(TestingLib.JUNIT)
    testImplementation(AndroidTestingLib.RETROFIT_MOCK)
    testImplementation(MockingLib.MOCK_COROUTINES)
    testImplementation(MockingLib.CORETESTING)
    androidTestImplementation(AndroidTestingLib.ANDROIDX_TEST_EXT_JUNIT)
    androidTestImplementation(AndroidTestingLib.ANDROIDX_TEST_RULES)
    androidTestImplementation(AndroidTestingLib.ESPRESSO_CORE)
}