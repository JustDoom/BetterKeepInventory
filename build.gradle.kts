plugins {
    `java-library`
    `maven-publish`
    id("com.gradleup.shadow") version "8.3.5" apply false
    id("xyz.jpenilla.run-paper") version "2.3.1" apply false
    id("org.spongepowered.gradle.plugin") version "2.3.0" apply false
}

if (!project.hasProperty("buildWithoutGitHash")) {
    fun getShortCommitHash(): Provider<String> = providers.exec {
        commandLine("git", "rev-parse", "--short", "HEAD")
    }.standardOutput.asText.map { it.trim().ifEmpty { "unknown" } }

    version = "${rootProject.version}-${getShortCommitHash().get()}"
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