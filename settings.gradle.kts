pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        jcenter()
        google()
        maven("https://dl.bintray.com/kotlin/kotlin-dev")
        maven("https://dl.bintray.com/kotlin/kotlin-eap")
    }
    resolutionStrategy {
        val kotlin_version: String by settings
        eachPlugin {
            when {
                requested.id.id == "com.android.library" ->
                    useModule("com.android.tools.build:gradle:${requested.version}")
                requested.id.id.startsWith("org.jetbrains.kotlin") ->
                    useVersion(kotlin_version)
            }
        }
    }
}
rootProject.name = "entities"

include("library")
include("examples:android")
include("examples")
include("glide")

enableFeaturePreview("GRADLE_METADATA")