import java.text.SimpleDateFormat
import java.util.*

plugins {
    id("java")
    id("java-library")
    id("idea")

    alias(libs.plugins.kotlin)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.forge)
    alias(libs.plugins.mixin)
    alias(libs.plugins.parchmentmc)
}

val modId = Constants.Mod.id
val minecraftVersion: String = libs.versions.minecraft.get()
val forgeMajorVersion: String = libs.versions.forge.get().split(".").first()
val kffVersion: String = libs.versions.kotlinForForge.get()
val jdkVersion = 17

base {
    archivesName = "${project.name}-$minecraftVersion"
    version = Constants.Mod.version
    group = Constants.Mod.group
}

mixin {
    add(sourceSets["main"], "${modId}.refmap.json")

    config("${modId}.mixins.json")
}

minecraft {
    mappings("parchment", "${libs.versions.parchmentmc.get()}-$minecraftVersion")

    copyIdeResources = true

    file("src/main/resources/META-INF/accesstransformer.cfg").takeIf(File::exists)?.let {
        accessTransformer(it)
    }

    runs {
        configureEach {
            ideaModule("${rootProject.name}.${project.name}.main")

            properties(
                mapOf(
                    "forge.logging.markers" to "REGISTRIES", "forge.logging.console.level" to "debug"
                )
            )

            jvmArgs(
                "-XX:+AllowEnhancedClassRedefinition"
            )

            mods {
                create(modId) {
                    source(sourceSets.main.get())
                }
            }
        }

        create("client") {
            taskName("Forge Client")

            workingDirectory(project.file("run"))

            property("forge.enabledGameTestNamespaces", modId)
        }

        create("server") {
            taskName("Forge Server")

            workingDirectory(project.file("run-server"))

            property("forge.enabledGameTestNamespaces", modId)
        }

        create("data") {
            taskName("Generate Data")

            workingDirectory(project.file("run-data"))

            args(
                "--mod",
                modId,
                "--all",
                "--output",
                file("src/generated/resources/"),
                "--existing",
                file("src/main/resources/")
            )
        }
    }
}


repositories {
    mavenCentral()
    maven {
        name = "Kotlin for Forge"
        url = uri("https://thedarkcolour.github.io/KotlinForForge/")
    }
    maven {
        name = "Sponge / Mixin"
        url = uri("https://repo.spongepowered.org/repository/maven-public/")
    }
    maven {
        name = "Bookshelf / BotanyPots"
        url = uri("https://maven.blamejared.com")
    }
    maven {
        name = "BotanyPotsTiers / Jade / TOP"
        url = uri("https://cursemaven.com")
    }
}

sourceSets["main"].resources.srcDir("src/generated/resources")

dependencies {
    minecraft(libs.minecraftForge)

    implementation(libs.kotlinForForge)

    implementation(deobf(libs.bookshelf))
    implementation(deobf(libs.botanypots))
    implementation(deobf(libs.botanypotstiers))

    implementation(deobf(libs.jade))
    implementation(deobf(libs.theoneprobe))

    runtimeOnly(deobf(libs.jei))

    annotationProcessor(variantOf(libs.mixin, "processor"))
    libs.mixinExtrasCommon.let {
        annotationProcessor(it)
        compileOnly(it)
    }
    libs.mixinExtrasForge.let {
        jarJar(it) {
            jarJar.ranged(this, "[${it.get().version},)")
        }
        implementation(it)
    }
}

val modDependencies = buildDeps(
    ModDep("forge", forgeMajorVersion),
    ModDep("minecraft", minecraftVersion),
    ModDep("kotlinforforge", kffVersion),
    ModDep("botanypots", "8.1", ordering = Order.AFTER),
    ModDep("botanypotstiers", "2.2", ordering = Order.AFTER, mandatory = false),
    ModDep("theoneprobe", "1.18-5.1", mandatory = false),
    ModDep("jade", "5.0", mandatory = false),
)

tasks {
    withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.release = jdkVersion
    }

    java {
        withSourcesJar()
        toolchain {
            languageVersion = JavaLanguageVersion.of(jdkVersion)
            vendor = JvmVendorSpec.JETBRAINS
        }
        JavaVersion.toVersion(jdkVersion).let {
            sourceCompatibility = it
            targetCompatibility = it
        }
    }

    processResources {
        val prop = mapOf(
            "version" to version,
            "group" to project.group,
            "minecraft_version" to minecraftVersion,
            "mod_loader" to "kotlinforforge",
            "mod_loader_version_range" to "[$kffVersion,)",
            "mod_name" to Constants.Mod.name,
            "mod_author" to Constants.Mod.author,
            "mod_id" to Constants.Mod.id,
            "license" to Constants.Mod.license,
            "description" to Constants.Mod.description,
            "display_url" to Constants.Mod.repositoryUrl,
            "issue_tracker_url" to Constants.Mod.issueTrackerUrl,

            "dependencies" to modDependencies
        )

        filesMatching(listOf("pack.mcmeta", "META-INF/mods.toml", "*.mixins.json")) {
            expand(prop)
        }
        inputs.properties(prop)
    }

    jar {
        from(rootProject.file("LICENSE")) {
            rename { "LICENSE_${Constants.Mod.id}" }
        }

        manifest {
            attributes(
                "Specification-Title" to Constants.Mod.name,
                "Specification-Vendor" to Constants.Mod.author,
                "Specification-Version" to version,
                "Implementation-Title" to project.name,
                "Implementation-Version" to version,
                "Implementation-Vendor" to Constants.Mod.author,
                "Implementation-Timestamp" to SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(Date()),
                "Timestamp" to System.currentTimeMillis(),
                "Built-On-Java" to "${System.getProperty("java.vm.version")} (${System.getProperty("java.vm.vendor")})",
                "Built-On-Minecraft" to minecraftVersion,
            )
        }

        finalizedBy("reobfJar")
    }

    named<Jar>("sourcesJar") {
        from(rootProject.file("LICENSE")) {
            rename { "LICENSE_${Constants.Mod.id}" }
        }
    }
}

fun deobf(dependency: Provider<MinimalExternalModuleDependency>) = fg.deobf(dependency.get())
