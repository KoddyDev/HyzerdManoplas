
plugins {
    java
    kotlin("jvm") version "1.6.10"
    `maven-publish`
}

group = "me.koddydev"
version = "1.0"

java.sourceCompatibility = JavaVersion.VERSION_1_8
java.targetCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
    maven("https://jitpack.io/")
    maven("https://m2.dv8tion.net/releases")
}

dependencies {
    compileOnly(kotlin("stdlib"))
    compileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")
    compileOnly("redis.clients:jedis:4.2.2")
    compileOnly("org.slf4j:slf4j-api:1.7.36")
    compileOnly("org.slf4j:slf4j-log4j12:1.7.36")
    compileOnly(files("C:\\Users\\Koddy\\Desktop\\Jars\\mkAPI-1.3.jar\\"))
    compileOnly(files("C:\\Users\\Koddy\\Desktop\\Jars\\EduardAPI-1.0-all.jar\\"))
}

tasks {
    jar {
        destinationDirectory
            .set(file("C:\\Users\\Koddy\\Desktop\\Jars\\Plugins\\"))
    }
    compileJava {
        options.encoding = "UTF-8"
    }
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}
publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}
