plugins {
    `java-library`
    eclipse
    idea
    `maven-publish`
    id("net.neoforged.gradle.userdev") version "7.0.145"
}

tasks.named<Wrapper>("wrapper") {
    distributionType = Wrapper.DistributionType.ALL
}

version = project.property("mod_version") as String
group = project.property("mod_group_id") as String

repositories {
    mavenLocal()
}

base {
    archivesName.set(project.property("mod_id") as String)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

idea {
    module {
        isDownloadSources = true
        isDownloadJavadoc = true
    }
}

val replaceProperties = mapOf(
    "minecraft_version" to project.property("minecraft_version"),
    "minecraft_version_range" to project.property("minecraft_version_range"),
    "neo_version" to project.property("neo_version"),
    "neo_version_range" to project.property("neo_version_range"),
    "loader_version_range" to project.property("loader_version_range"),
    "mod_id" to project.property("mod_id"),
    "mod_name" to project.property("mod_name"),
    "mod_license" to project.property("mod_license"),
    "mod_version" to project.property("mod_version"),
    "mod_authors" to project.property("mod_authors"),
    "mod_description" to project.property("mod_description")
)

tasks.withType<ProcessResources> {
    inputs.properties(replaceProperties)
    filesMatching("META-INF/neoforge.mods.toml") {
        expand(replaceProperties)
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
    repositories {
        maven {
            url = uri("file://${project.projectDir}/repo")
        }
    }
}

sourceSets {
    named("main") {
        resources {
            srcDir("src/generated/resources")
        }
    }
}

configurations {
    runtimeClasspath {
        extendsFrom(configurations["localRuntime"])
    }
}

dependencies {
    implementation("net.neoforged:neoforge:${project.property("neo_version")}")
}

tasks.register<JavaExec>("client") {
    jvmArgs = listOf("-XX:+IgnoreUnrecognizedVMOptions", "-XX:+AllowEnhancedClassRedefinition")
    systemProperty("forge.logging.markers", "REGISTRIES")
    systemProperty("forge.logging.console.level", "debug")
    systemProperty("forge.enabledGameTestNamespaces", project.property("mod_id") as String)
    args = listOf("--username", "mayday")
    classpath = sourceSets["main"].runtimeClasspath
}

tasks.register<JavaExec>("server") {
    systemProperty("forge.enabledGameTestNamespaces", project.property("mod_id") as String)
    args = listOf("--nogui")
    classpath = sourceSets["main"].runtimeClasspath
}

tasks.register<JavaExec>("gameTestServer") {
    systemProperty("forge.enabledGameTestNamespaces", project.property("mod_id") as String)
    classpath = sourceSets["main"].runtimeClasspath
}

tasks.register<JavaExec>("data") {
    args = listOf("--mod", project.property("mod_id") as String, "--all", "--output", file("src/generated/resources/").absolutePath, "--existing", file("src/main/resources/").absolutePath)
    classpath = sourceSets["main"].runtimeClasspath
}