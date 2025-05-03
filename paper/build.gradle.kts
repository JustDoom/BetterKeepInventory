plugins {
    `java-library`
    `maven-publish`
    id("com.gradleup.shadow")
    id("xyz.jpenilla.run-paper")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.4-R0.1-SNAPSHOT")
    compileOnly("com.github.amnoah.betterreload:api:v1.0.0")
    implementation("org.bstats:bstats-bukkit:3.1.0")
    implementation(project(":common"))
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks {
    shadowJar {
        dependencies {
            include(dependency("org.bstats:bstats-bukkit:3.1.0"))
            include(dependency("org.bstats:bstats-base:3.1.0"))
            include(project(":common"))
        }
        relocate("org.bstats", "com.imjustdoom.betterkeepinventory.paper.bstats")
        relocate("com.imjustdoom.betterkeepinventory.common", "com.imjustdoom.betterkeepinventory.common")
        archiveFileName.set("${rootProject.name}-paper-${project.version}.jar")
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