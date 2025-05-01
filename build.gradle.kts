plugins {
    `java-library`
    `maven-publish`
    id("com.gradleup.shadow") version "8.3.5"
    id("xyz.jpenilla.run-paper") version "2.3.1"
}

repositories {
    mavenLocal()
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }

    maven {
        url = uri("https://jitpack.io")
    }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")
    compileOnly("com.github.amnoah.betterreload:api:v1.0.0")
    implementation("org.bstats:bstats-bukkit:3.1.0")
}

group = "com.imjustdoom.betterkeepinventory"
version = "2.0.0"
description = "BetterKeepInventory"
java.sourceCompatibility = JavaVersion.VERSION_21

tasks {
    shadowJar {
        dependencies {
            include(dependency("org.bstats:.*"))
        }
        relocate("org.bstats", "com.imjustdoom.betterkeepinventory.bstats")
    }
    runServer {
        minecraftVersion("1.21.4")
        dependsOn(shadowJar)
    }
    build {
        dependsOn(shadowJar)
    }
    processResources {
        val pluginName = rootProject.name
        val pluginVersion = project.version.toString()
        val pluginGroup = project.group.toString()

        filesMatching("**/plugin.yml") {
            expand(
                "name" to pluginName,
                "version" to pluginVersion,
                "group" to pluginGroup
            )
        }
    }
    withType<JavaCompile>() {
        options.encoding = "UTF-8"
    }
    withType<Javadoc>() {
        options.encoding = "UTF-8"
    }
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}
