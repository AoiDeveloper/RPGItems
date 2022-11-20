plugins {
    kotlin("jvm") version "1.7.21"
    id("org.jetbrains.dokka") version "1.7.20"
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
    dokkaHtmlPlugin("org.jetbrains.dokka:kotlin-as-java-plugin:1.7.20")

    implementation("org.jetbrains.kotlin:kotlin-stdlib:${kotlin_version}")
    compileOnly("org.spigotmc:spigot-api:${minecraft_version}-R0.1-SNAPSHOT")
}

tasks.withType(Jar::class) {
    manifest {
        attributes["Main-Class"] = "package.to.MainKt"
    }
    from(
        configurations.runtimeClasspath.map { it ->
            it.toList().map {
                if(it.isDirectory) it else zipTree(it)
            }
        }
    )
    duplicatesStrategy = DuplicatesStrategy.INCLUDE
}