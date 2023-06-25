@file:Suppress("UNUSED_VARIABLE")

plugins {
    kotlin("multiplatform") version "1.8.10"
    kotlin("plugin.serialization") version "1.8.10"
    application
}

val kotlinxSerializationVersion = project.property("kotlinx.serialization.version") as String
val kotlinxCoroutinesVersion = project.property("kotlinx.coroutines.version") as String
val ktorVersion = project.property("ktor.version") as String
val kotlinWrappersSuffix = project.property("kotlin.wrappers.suffix") as String

val kotlinEmotionVersion: String by project
val kotlinReactVersion: String by project

val kotlinHtmlVersion = project.property("kotlinx.html.version") as String
val exposedVersion = project.property("exposed.version") as String
val h2Version = project.property("h2.version") as String

group = "me.neversleeps.kotlin"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js(IR) {
        binaries.executable()
        browser()
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinxSerializationVersion")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(kotlin("stdlib-jdk8"))
                implementation("io.ktor:ktor-server-netty:$ktorVersion")
                implementation("io.ktor:ktor-server-html-builder:$ktorVersion")
                implementation("io.ktor:ktor-server-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-serialization-jackson:$ktorVersion")
                implementation("io.ktor:ktor-client-apache:$ktorVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:$kotlinHtmlVersion")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-css:$kotlinWrappersSuffix")
                implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
                implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
                implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
                implementation("com.h2database:h2:$h2Version")
            }
        }
        val jvmTest by getting
        val jsMain by getting {
            dependencies {
                implementation(kotlin("stdlib-js"))
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react:$kotlinReactVersion")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:$kotlinReactVersion")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-emotion:$kotlinEmotionVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-js:$kotlinxCoroutinesVersion")
            }
        }
        val jsTest by getting
    }
}

application {
    mainClass.set("me.neversleeps.ServerKt")
}

tasks.named<Copy>("jvmProcessResources") {
    val jsBrowserDistribution = tasks.named("jsBrowserDistribution")
    from(jsBrowserDistribution)
}

tasks.named<JavaExec>("run") {
    dependsOn(tasks.named<Jar>("jvmJar"))
    classpath(tasks.named<Jar>("jvmJar"))
}
