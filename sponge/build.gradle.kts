import org.spongepowered.gradle.plugin.config.PluginLoaders
import org.spongepowered.plugin.metadata.model.PluginDependency

plugins {
    `java-library`
    `maven-publish`
    id("com.gradleup.shadow")
    id("org.spongepowered.gradle.plugin")
}

dependencies {
    implementation(project(":common"))
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

sponge {
    apiVersion("13.0.0")
    license("GPL3")
    loader {
        name(PluginLoaders.JAVA_PLAIN)
        version(project.version as String?)
    }
    plugin("betterkeepinventory") {
        displayName("BetterKeepInventory")
        version(project.version as String?)
        entrypoint((project.group as String?) + ".sponge." + (rootProject.name) + "Sponge")
        description("BetterKeepInventory")
        links {
            homepage("https://imjustdoom.com")
            source("https://github.com/JustDoom/BetterKeepInventory")
            issues("https://github.com/JustDoom/BetterKeepInventory/issues")
        }
        contributor("JustDoom") {
            description("Creator")
        }
        dependency("spongeapi") {
            loadOrder(PluginDependency.LoadOrder.AFTER)
            optional(false)
        }
    }
}

tasks {
    shadowJar {
        dependencies {
            include(project(":common"))
        }
        relocate("com.imjustdoom.betterkeepinventory.common", "com.imjustdoom.betterkeepinventory.common")
        archiveFileName.set("${rootProject.name}-sponge-${project.version}.jar")
    }

    build {
        dependsOn(shadowJar)
    }

    withType<JavaCompile> {
        options.encoding = "UTF-8"
    }
    withType<Javadoc> {
        options.encoding = "UTF-8"
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}