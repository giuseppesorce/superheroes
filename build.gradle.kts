buildscript {
    repositories {
        google()
        maven {
            url = uri("https://maven.google.com")
        }

        mavenCentral()

    }
    dependencies {

        classpath("com.android.tools.build:gradle:${Vers.GRADLE}")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${Vers.KOTLIN}")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:${Vers.HILT}")
        classpath ("org.jetbrains.kotlin:kotlin-serialization:${Vers.KOTLIN}")
    }
}

allprojects {
    repositories {
        google()
        maven {
            url = uri("https://maven.google.com")
        }
        mavenCentral()
    }
    configurations.all {
        // this resolves M1 problem with sqlite
        resolutionStrategy {
            force( "org.xerial:sqlite-jdbc:3.34.0")
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
