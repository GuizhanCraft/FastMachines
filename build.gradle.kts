import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm") version "2.1.10"
    id("com.gradleup.shadow") version "8.3.9"
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
    id("xyz.jpenilla.run-paper") version "2.3.1"
}

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://central.sonatype.com/repository/maven-snapshots/")
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.alessiodp.com/releases/")
    maven("https://jitpack.io")
}

dependencies {
    fun compileOnlyAndTestImpl(dependencyNotation: Any) {
        compileOnly(dependencyNotation)
        testImplementation(dependencyNotation)
    }

    compileOnly(kotlin("stdlib")) // loaded through library loader
    compileOnly(kotlin("reflect")) // loaded through library loader
    compileOnlyAndTestImpl("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")
    compileOnlyAndTestImpl("com.github.slimefun:Slimefun4:experimental-SNAPSHOT")
    compileOnly("net.guizhanss:SlimefunTranslation:e6da231617")
    compileOnly("com.github.schntgaispock:SlimeHUD:1.3.0")
    compileOnly("com.github.SlimefunGuguProject:InfinityExpansion:bebf0bd0f9")
    compileOnly("com.github.VoperAD:SlimeFrame:8af2379a01")
    compileOnly("net.guizhanss:InfinityExpansion2:8d3e6c40f6")
    implementation("org.bstats:bstats-bukkit:3.1.0")
    implementation("net.guizhanss:guizhanlib-all:2.4.0-SNAPSHOT")
    implementation("net.guizhanss:guizhanlib-kt-all:0.2.0")

    testImplementation(kotlin("test"))
    testImplementation("org.mockbukkit.mockbukkit:mockbukkit-v1.21:4.10.0")
}

group = "net.guizhanss"
description = "FastMachines"

val mainPackage = "net.guizhanss.fastmachines"

java {
    disableAutoTargetJvm()
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        javaParameters = true
        jvmTarget = JvmTarget.JVM_17
    }
}

tasks.shadowJar {
    fun doRelocate(from: String, to: String? = null) {
        val last = to ?: from.split(".").last()
        relocate(from, "$mainPackage.libs.$last")
    }

    doRelocate("net.byteflux.libby")
    doRelocate("net.guizhanss.guizhanlib")
    doRelocate("org.bstats")
    doRelocate("io.papermc.lib", "paperlib")
    minimize()
    archiveClassifier = ""
}

bukkit {
    main = "$mainPackage.FastMachines"
    apiVersion = "1.18"
    authors = listOf("ybw0014")
    description = "More Slimefun machines that bulk craft items with all shapeless recipes"
    depend = listOf("Slimefun")
    softDepend = listOf(
        "GuizhanLibPlugin",
        "SlimefunTranslation",
        "InfinityExpansion",
        "SlimeFrame",
        "SlimeHUD",
        "InfinityExpansion2",
    )
    loadBefore = listOf(
        "SlimeCustomizer",
        "RykenSlimeCustomizer",
        "SlimeFunRecipe"
    )
}

tasks.runServer {
    downloadPlugins {
        // Slimefun
        url("https://blob.build/dl/Slimefun4/Dev/latest")
        // SlimeHUD
        url("https://blob.build/dl/SlimeHUD/Dev/latest")
        // GuizhanCraft for testing convenient
        url("https://builds.guizhanss.com/api/download/ybw0014/GuizhanCraft/master/latest")
    }
    jvmArgs("-Dcom.mojang.eula.agree=true")
    minecraftVersion("1.20.6")
}

tasks.test {
    useJUnitPlatform()
}
