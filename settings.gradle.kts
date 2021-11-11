rootProject.name = "kotlin-live-timing"
include("live-timing-commons", "live-timing-view")
pluginManagement {
    repositories {
        mavenCentral()
        maven { setUrl("https://dl.bintray.com/kotlin/kotlin-eap") }
        maven { setUrl("https://plugins.gradle.org/m2/") }
    }
}

