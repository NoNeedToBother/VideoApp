package ru.kpfu.itis.paramonov.navigation

sealed class Routes(val route: String) {
    data object VideosScreen: Routes("videosScreen")
    data object VideoScreen: Routes("videoScreen")
}