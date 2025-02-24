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
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "VideoApp"
include(":app")
include(":core")
include(":core:network")
include(":feature")
include(":feature:videos")
include(":feature:videos:api")
include(":feature:videos:impl")
include(":core:navigation")
include(":core:database")
include(":core:core")
