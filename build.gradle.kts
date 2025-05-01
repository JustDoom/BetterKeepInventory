plugins {
    `java-library`
    `maven-publish`
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
}

group = "com.imjustdoom.betterkeepinventory"
version = "2.0.0"
description = "BetterKeepInventory"
java.sourceCompatibility = JavaVersion.VERSION_21

tasks.processResources {
    filesMatching("**/plugin.yml") {
        expand(
            "name" to rootProject.name,
            "version" to project.version,
            "group" to project.group.toString()
        )
    }
}

tasks {
    runServer {
        minecraftVersion("1.21.4")
    }
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc>() {
    options.encoding = "UTF-8"
}
