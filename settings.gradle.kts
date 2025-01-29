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
//        maven(url = java.net.URI("https://jcenter.bintray.com"))
        maven (url ="https://repository.liferay.com/nexus/content/repositories/public/" )
    }
}

rootProject.name = "Best Book"
include(":app")
