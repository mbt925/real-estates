@file:Suppress("UnstableApiUsage")

pluginManagement {
    includeBuild("plugins")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "real-estates"
include(":app")
include(":feature")
include(":feature:api")
include(":feature:ui")
include(":core")
include(":core:test")
include(":networking")
include(":service")
include(":design")
