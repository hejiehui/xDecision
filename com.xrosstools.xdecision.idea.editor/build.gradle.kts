plugins {
    id("java")
    id("org.jetbrains.intellij.platform") version "2.10.2"
}

group = "com.xrosstools"
version = "3.3.2"

val sandbox  : String by project

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

// Read more: https://plugins.jetbrains.com/docs/intellij/tools-intellij-platform-gradle-plugin.html
dependencies {
    intellijPlatform {
        intellijIdeaUltimate("2025.3.3")
        bundledPlugin("com.intellij.java")
    }

    compileOnly(files("$sandbox/com.xrosstools.idea.gef.zip"))
    implementation(files("libs/dom4j-1.6.1.jar"))
}

intellijPlatform {
    instrumentCode = true
    buildSearchableOptions = false

    pluginConfiguration {
        //<idea-version since-build="193.6911.18"/>
        ideaVersion {
            sinceBuild = "183.6156.11"
        }

        changeNotes = """
            <em>3.3.2</em> Minor code optimize for data parsing.<br>
            <em>3.3.1</em> FIX NPE when remove expression from node and identify true/false.<br>
            <em>3.3.0</em> Improve user defined data type import, default parser and evaluator, test case generation.<br>
            <em>3.2.0</em> Support decision node description property.<br>
            <em>3.1.3</em> Fix visual inconsistency between toolbar and main window.<br>
            <em>3.1.2</em> Fix create model file bug.<br>
            <em>3.1.1</em> Fix misplace of connection condition when value changes.<br>
            <em>3.1.0</em> Make the product qualify for market standard.<br>
            <em>3.0.10</em> Fix IconLoader usage<br>
            <em>3.0.9</em> Fix create decision NPE bug<br>
            <em>3.0.8</em> Fix bracket overlapping issue<br>
            <em>3.0.7</em> Update to IDEA GEF 1.0.5 to fix refresh children parts<br>
            <em>3.0.6</em> Fix connection expression display bug. Support remove parameter from parameter list<br>
            <em>3.0.5</em> Fix paring parameters in BETWEEN and IN.<br>
            <em>3.0.4</em> Support old version modle file.<br>
            <em>3.0.3</em> Fix unit test and optimize model reading.<br>
            <em>3.0.2</em> Revise color and expression validation.<br>
            <em>3.0.1</em> Remove printing unit test code to console<br>
            <em>3.0.0</em> IDEA version that supports customer type and expression<br>
        """.trimIndent()
    }

    pluginVerification {
        ides {
            // 验证最老和最新的目标版本
            ide("IC-2018.3.6")
            ide("IC-2020.3.4")
            ide("IC-2025.3.3")
        }
    }
}

intellijPlatformTesting {
    runIde {
        register("runWithLocalPlugins") {
            plugins {
                val pluginFiles = file(sandbox).listFiles()
                pluginFiles.forEach { file ->
                    if (!file.name.contains(project.name)) {
                        localPlugin(file.absolutePath)
                    }
                }
            }
        }
    }
}

tasks.named("runIde") {
    dependsOn("runWithLocalPlugins")
}

tasks {
    buildPlugin {
        archiveFileName.set("${project.name}.zip")
    }
}
