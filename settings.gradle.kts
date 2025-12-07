pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS) // Обычно это тоже нужно, но если не ругается - оставьте как есть
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") } // <-- ИСПРАВЛЕНИЕ ЗДЕСЬ
    }
}

rootProject.name = "My Application"
include(":app")
