plugins {
    id ("com.android.library")
    kotlin("android")
    id  ("kotlin-kapt")
}



android {
    compileSdkVersion(Sdk.COMPILE_SDK_VERSION)
    buildToolsVersion("30.0.3")

    defaultConfig {

        minSdkVersion(Sdk.MIN_SDK_VERSION)
        targetSdkVersion(Sdk.TARGET_SDK_VERSION)
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        //consumerProguardFiles = "consumer-rules.pro"
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
        jvmTarget = "1.8"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }
    lintOptions {
        isWarningsAsErrors = true
        isAbortOnError = true
    }

    packagingOptions {
        exclude ("classes.dex")
        exclude ( "**.**")
    }

    buildFeatures {
        viewBinding = true
    }
}

dependencies {



    implementation(Deps.KOTLIN_STLIB)
    implementation(Deps.coreKtx)
    implementation(Deps.appCompact)
    implementation(Deps.constraintLayout)
}