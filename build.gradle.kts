// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript{
    repositories{
        mavenCentral()
    }
    extra.apply {
        set("room_version", "2.5.2")
    }
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.44.2")
    }
}
plugins {
    id("com.android.application") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.21" apply false
}
