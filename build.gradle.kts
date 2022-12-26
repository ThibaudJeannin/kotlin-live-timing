import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("multiplatform") version "1.7.21"
    application
    kotlin("plugin.serialization") version "1.7.21"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "me.thibaud"
version = "1.0-SNAPSHOT"

val versionKtor = "2.2.1"
val versionCoroutines = "1.6.4"
val versionReact = "18.2.0-pre.457"
val versionStyledComponents = "5.3.6"
val versionKotlinStyled = "5.3.6-pre.457"

repositories {
    mavenCentral()
    google()
    maven("https://maven.pkg.jetbrains.space/public/p/ktor/eap")
}

kotlin {
    jvm {
        withJava()
    }
    js {
        browser {
            commonWebpackConfig {
                cssSupport() {
                    this.enabled = true
                }
            }
            testTask {
                enabled = false
                useKarma {
                    useFirefox()
                }
            }
        }
        binaries.executable()
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-core:$versionKtor")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$versionKtor")
                implementation("io.ktor:ktor-client-content-negotiation:$versionKtor")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("io.ktor:ktor-serialization:$versionKtor")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$versionKtor")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$versionCoroutines")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$versionCoroutines")
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation("io.ktor:ktor-server-core:$versionKtor")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$versionKtor")
                implementation("io.ktor:ktor-server-content-negotiation:$versionKtor")
                implementation("io.ktor:ktor-serialization:$versionKtor")
                implementation("io.ktor:ktor-server-compression:$versionKtor")
                implementation("io.ktor:ktor-server-cors:$versionKtor")
                implementation("io.ktor:ktor-server-netty:$versionKtor")
                implementation("io.ktor:ktor-client-cio:$versionKtor")
                implementation("ch.qos.logback:logback-classic:1.2.10")
            }
        }

        val jsMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react:$versionReact")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:$versionReact")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-styled:$versionKotlinStyled")
                implementation("io.ktor:ktor-client-js:$versionKtor")
                implementation("io.ktor:ktor-client-content-negotiation:$versionKtor")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$versionKtor")
                implementation(npm("styled-components", "~$versionStyledComponents"))
            }
        }
    }
}

application {
    mainClass.set("ServerKt")
}

// include JS artifacts in any JAR we generate
tasks.getByName<Jar>("jvmJar") {
    val taskName = if (project.hasProperty("isProduction")
        || project.gradle.startParameter.taskNames.contains("installDist")
    ) {
        "jsBrowserProductionWebpack"
    } else {
        "jsBrowserDevelopmentWebpack"
    }
    val webpackTask = tasks.getByName<org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack>(taskName)
    dependsOn(webpackTask) // make sure JS gets compiled first
    from(File(webpackTask.destinationDirectory, webpackTask.outputFileName))
}

// include JS artifacts in shadowJar
tasks.getByName<ShadowJar>("shadowJar") {
    val taskName = if (project.hasProperty("isProduction")
        || project.gradle.startParameter.taskNames.contains("installDist")
    ) {
        "jsBrowserProductionWebpack"
    } else {
        "jsBrowserDevelopmentWebpack"
    }
    val webpackTask = tasks.getByName<org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack>(taskName)
    dependsOn(webpackTask) // make sure JS gets compiled first
    from(File(webpackTask.destinationDirectory, webpackTask.outputFileName))
}

tasks.getByName<JavaExec>("run") {
    classpath(tasks.getByName<Jar>("jvmJar")) // so that the JS artifacts generated by `jvmJar` can be found and served
}

// Alias "installDist" as "stage" (for cloud providers)
tasks.create("stage") {
    dependsOn(tasks.getByName("installDist"))
}

// only necessary until https://youtrack.jetbrains.com/issue/KT-37964 is resolved
distributions {
    main {
        contents {
            from("$buildDir/libs") {
                rename("${rootProject.name}-jvm", rootProject.name)
                into("lib")
            }
        }
    }
}


rootProject.plugins.withType(org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin::class.java) {
    rootProject.the<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension>().versions.webpackDevServer.version =
        "4.11.1 "
}

rootProject.extensions.configure<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension> {
    versions.webpackCli.version = "4.10.0"
}

rootProject.plugins.withType(org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin::class.java) {
    rootProject.the<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension>().disableGranularWorkspaces()
}