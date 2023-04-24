pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}
rootProject.name = "entities"

include("core")
include("examples:android")
include("examples")
include("glide")
include("picasso")