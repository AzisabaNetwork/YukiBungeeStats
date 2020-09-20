import java.util.*

plugins {
    kotlin("jvm") version "1.4.10"
    id("com.github.johnrengelman.shadow") version "6.0.0"
}

group = "net.azisaba"
version = "1.0.1"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
    implementation(kotlin("stdlib"))
    compileOnly("io.github.waterfallmc:waterfall-api:1.16-R0.4-SNAPSHOT")
}

tasks {
    compileKotlin { kotlinOptions.jvmTarget = "1.8" }
    compileTestKotlin { kotlinOptions.jvmTarget = "1.8" }

    processResources {
        filesMatching("**/bungee.yml") {
            filter { it.replace("%version", "$version") }
        }
    }

    shadowJar {
        relocate("kotlin", UUID.randomUUID().toString())
        relocate("org.jetbrains.annotations", UUID.randomUUID().toString())

        minimize()
    }
}
