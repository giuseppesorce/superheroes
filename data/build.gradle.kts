plugins {
    id ("com.android.library")
    kotlin("android")
    kotlin ("kapt")
    id ("dagger.hilt.android.plugin")
    id ("kotlinx-serialization")
}
android {
    compileSdkVersion(Sdk.COMPILE_SDK_VERSION)
    buildToolsVersion("30.0.3")
    //flavorDimensions "milk"
    defaultConfig {
        minSdkVersion(Sdk.MIN_SDK_VERSION)
        targetSdkVersion(Sdk.TARGET_SDK_VERSION)
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "MARVELPUBLICKEY", ConstantsBuild.MARVEL_PUBLIC_KEY)
        buildConfigField("String", "MARVELPRIVATEKEY", ConstantsBuild.MARVEL_PRIVATE_KEY)
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

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation (Deps.KOTLIN_STLIB)
    implementation(project(":commons"))
   implementation(project(":persistence"))
    //dipendencies
    implementation(Deps.coreKtx)
    implementation(Deps.appCompact)
    implementation(Deps.constraintLayout)

    implementation(DI.DAGGER_DEP)
    kapt(DI.DAGGER_ANNOTATION)
    // hilt
    implementation(DI.HILT_DEP)
    kapt(DI.HILT_KAPT)
    kaptAndroidTest(DI.HILT_ANDROID_KAPT_TEST)
    kaptTest(DI.HILT_KAPT_TEST)

    // Retrofit
    implementation(Network.RETROFIT)
    implementation(Network.OKHTTP)
    implementation(Network.KOTLIN_SER_CONVERTER)
    // Interceptors
    implementation(Network.LOGGIG_INTERCEPTOR)
    // Coroutines
    implementation(Kotlin.COROUTINES)
    implementation(Kotlin.COROUTINES_ANDROID)


    //Mock
    testImplementation(MockingLib.MOCKK_DEP)
    testImplementation(MockingLib.CORETESTING)
    testImplementation(MockingLib.MOCK_COROUTINES)
    testImplementation(AndroidTestingLib.RETROFIT_MOCK)


    //TESTING
    testImplementation(TestingLib.JUNIT)
    androidTestImplementation(AndroidTestingLib.ANDROIDX_TEST_EXT_JUNIT)
    androidTestImplementation(AndroidTestingLib.ANDROIDX_TEST_RULES)
    androidTestImplementation(AndroidTestingLib.ESPRESSO_CORE)
}