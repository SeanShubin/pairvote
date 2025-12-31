import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
}

kotlin {
    jvm()

//    js {
//        browser()
//        binaries.executable()
//    }

    js(IR) {
        browser {
            commonWebpackConfig {
                devtool = "source-map"
                sourceMaps = true
            }
        }
        binaries.executable()
    }

    sourceSets {
        // Shared: Business logic + Resources (needed for generated code)
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.components.resources)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }

        // Desktop: Compose Multiplatform + Material3
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.foundation)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.kotlinx.coroutinesSwing)
        }

        // Web: Compose HTML (no WASM)
        jsMain.dependencies {
            implementation(compose.html.core)
        }
    }
}


compose.desktop {
    application {
        mainClass = "com.seanshubin.pairvote.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "com.seanshubin.pairvote"
            packageVersion = "1.0.0"
        }
    }
}
