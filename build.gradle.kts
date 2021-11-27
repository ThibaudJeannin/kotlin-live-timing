plugins {
    kotlin("multiplatform") version "1.5.31"
    application
    kotlin("plugin.serialization") version "1.5.31"
}

group = "me.thibaud"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    google()
}

kotlin {
    jvm{
        withJava()
    }
    js {
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
            testTask {
                enabled = false
            }
        }
        binaries.executable()
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.1")
                implementation("io.ktor:ktor-client-core:1.6.6")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation("io.ktor:ktor-serialization:1.6.6")
                implementation("io.ktor:ktor-server-core:1.6.6")
                implementation("io.ktor:ktor-server-tomcat:1.6.6")
                implementation("ch.qos.logback:logback-classic:1.2.7")
                implementation("org.litote.kmongo:kmongo-coroutine-serialization:4.4.0")
            }
        }

        val jsMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react:17.0.2-pre.270-kotlin-1.6.0")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-react-dom:17.0.2-pre.270-kotlin-1.6.0")
                implementation("org.jetbrains.kotlin-wrappers:kotlin-styled:5.3.3-pre.270-kotlin-1.6.0")
                implementation("io.ktor:ktor-client-js:1.6.6")
                implementation("io.ktor:ktor-client-json:1.6.6")
                implementation("io.ktor:ktor-client-serialization:1.6.6")
                implementation(npm("styled-components", "~5.3.3"))
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
    from(File(webpackTask.destinationDirectory, webpackTask.outputFileName)) // bring output file along into the JAR
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
        "4.4.0"
}

rootProject.extensions.configure<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension> {
    versions.webpackCli.version = "4.9.0"
}

rootProject.plugins.withType(org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin::class.java) {
    rootProject.the<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension>().disableGranularWorkspaces()
}