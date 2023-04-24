allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
        google()
    }
}

buildscript {
    val kotlin_version: String by project
    val android_tools_version: String by project

    repositories {
        mavenLocal()
        google()
        mavenCentral()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version")
        classpath("com.android.tools.build:gradle:$android_tools_version")
    }
}