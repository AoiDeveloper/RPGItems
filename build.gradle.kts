plugins {
    kotlin("jvm") version "1.7.21"
}

val kotlin_version: String by project
val minecraft_version: String by project
group = "net.aoideveloper"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib:${kotlin_version}")
    compileOnly("org.spigotmc:spigot-api:${minecraft_version}-R0.1-SNAPSHOT")
}