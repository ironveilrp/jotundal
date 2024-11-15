/*
 *  Jotundal
 *  Copyright (C)  2024 Martijn Heil
 *
 *  This program is free software: you can redistribute it and/or modify it
 *  under the terms of the GNU Lesser General Public License as published by the
 *  Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful, but WITHOUT
 *  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 *  FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 *  for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.apache.tools.ant.filters.ReplaceTokens
import org.gradle.plugins.ide.idea.model.IdeaLanguageLevel
import java.net.URI

plugins {
    `java-gradle-plugin`
    `java-library`
    kotlin("jvm") version "1.9.21"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    idea
    `maven-publish`
}

group = "com.github.ironveilrp"
version = "1.0.0"
description = "jotundal"

apply {
    plugin("java")
    plugin("java-library")
    plugin("kotlin")
    plugin("com.github.johnrengelman.shadow")
    plugin("idea")
    plugin("maven-publish")
}

kotlin {
    jvmToolchain(21)
}

tasks {
    withType<ProcessResources> {
        filter(mapOf(Pair("tokens", mapOf(Pair("version", version)))), ReplaceTokens::class.java)
    }
    withType<ShadowJar> {
        //this.classifier = null
        this.configurations = listOf(project.configurations.shadow.get())
    }
}

defaultTasks = mutableListOf("build")

repositories {
    maven { url = URI("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") }
    maven { url = URI("https://jitpack.io") }
    mavenCentral()
    mavenLocal()
}

idea {
    project {
        languageLevel = IdeaLanguageLevel("21")
    }
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}

dependencies {
    implementation("org.spigotmc:spigot-api:1.21.1-R0.1-SNAPSHOT") { isChanging = true }
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0-RC2")
}
