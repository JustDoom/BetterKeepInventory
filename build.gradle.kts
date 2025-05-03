plugins {
    `java-library`
    `maven-publish`
    id("com.gradleup.shadow") version "8.3.5" apply false
    id("xyz.jpenilla.run-paper") version "2.3.1" apply false
    id("org.spongepowered.gradle.plugin") version "2.3.0" apply false
}

allprojects {
    group = "com.imjustdoom.betterkeepinventory"
    version = rootProject.version
    description = "BetterKeepInventory"

    repositories {
        mavenLocal()
        maven {
            name = "papermc"
            url = uri("https://repo.papermc.io/repository/maven-public/")
        }
        maven {
            name = "sponge"
            url = uri("https://repo.spongepowered.org/repository/maven-public/")
        }
        maven {
            url = uri("https://jitpack.io")
        }
    }
}