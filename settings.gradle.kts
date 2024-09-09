pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS) // Preferência de repositórios nos settings
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "HexagonApp"
include(":app")
