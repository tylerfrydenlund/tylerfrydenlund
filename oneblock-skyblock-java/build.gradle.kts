import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("com.github.johnrengelman.shadow") version "7.1.1"
    id("java")
    kotlin("jvm") version "1.8.20"
    kotlin("plugin.lombok") version "1.7.20"
}


group = "com.crimsoncentral.firecraft"
version = "0.8.0"

val minecraftVersion: String = "1.20.4"

repositories {

    mavenCentral()
    maven {
        name = "papermc-repo"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
    maven {
        name = "sonatype"
        url = uri("https://oss.sonatype.org/content/groups/public/")
    }
    maven {
        name = "enginehub"
        url = uri("https://maven.enginehub.org/repo/")
    }

    maven { url = uri("https://jitpack.io") }
}


dependencies {

    testImplementation(kotlin("test"))


    compileOnly("io.papermc.paper:paper-api:$minecraftVersion-R0.1-SNAPSHOT")

    compileOnly("com.sk89q.worldedit:worldedit-bukkit:7.2.9")

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("com.google.guava:guava:31.1-jre")
    implementation("org.apache.commons:commons-pool2:2.11.1")
    implementation("com.squareup.okhttp:okhttp:2.7.5")
    implementation(kotlin("script-runtime"))

    implementation("org.zeroturnaround:zt-zip:1.16")

    testImplementation("junit:junit:4.13.2")

    implementation("com.google.code.gson:gson:2.9.0")
    compileOnly("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.projectlombok:lombok:1.18.24")

    testCompileOnly("org.projectlombok:lombok:1.18.24")
    testAnnotationProcessor("org.projectlombok:lombok:1.18.24")

    implementation("net.kyori:adventure-text-serializer-plain:4.11.0")
    compileOnly("com.sk89q.worldedit:worldedit-bukkit:7.2.0")
    implementation(kotlin("stdlib-jdk8"))

}

tasks.withType<Test> {
    useJUnitPlatform()
}


tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict", "-Xcontext-receivers")
        jvmTarget = "17"
    }
}

tasks.withType<ProcessResources> {

    fun toMajorMinor(version: String): String {
        val (major, minor) = version.split(".")
        return "$major.$minor"
    }

    val props : Map<String, Any> = mapOf("version" to version, "minecraftVersion" to toMajorMinor(minecraftVersion))

    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand(props)
    }
}

tasks.withType<Jar> {

    if (!name.contains("shadowJar")) {
        return@withType
    }

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    from({
        configurations.runtimeClasspath.get().map { zipTree(it) }
    })

    from(sourceSets.main.get().output)

    destinationDirectory.set(file("./srv/plugins"))
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    from(sourceSets.main.get().output)

    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })

    exclude("META-INF/*.SF", "META-INF/*.DSA", "META-INF/*.RSA")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<ShadowJar> {
    mergeServiceFiles()
}
