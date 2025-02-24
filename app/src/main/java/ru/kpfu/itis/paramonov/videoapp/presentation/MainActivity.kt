package ru.kpfu.itis.paramonov.videoapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.koin.android.ext.koin.androidContext
import org.koin.compose.KoinApplication
import ru.kpfu.itis.paramonov.database.external.di.databaseModule
import ru.kpfu.itis.paramonov.navigation.Routes
import ru.kpfu.itis.paramonov.network.external.di.networkModule
import ru.kpfu.itis.paramonov.videoapp.di.adapterModule
import ru.kpfu.itis.paramonov.videoapp.di.commonModule
import ru.kpfu.itis.paramonov.videos.di.featureVideosModule
import ru.kpfu.itis.paramonov.videos.presentation.ui.screens.VideoScreen
import ru.kpfu.itis.paramonov.videos.presentation.ui.screens.VideosScreen

class MainActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            KoinApplication(
                application = {
                    androidContext(this@MainActivity)
                    modules(
                        commonModule,
                        networkModule,
                        databaseModule,
                        featureVideosModule,
                        adapterModule,
                    )
                }
            ) {
                Scaffold { paddingValues ->
                    ScreenContent(
                        modifier = Modifier.fillMaxSize()
                            .padding(paddingValues)
                    )
                }
            }
        }
    }
}

@Composable
fun ScreenContent(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Routes.VideosScreen.route,
    ) {
        composable(Routes.VideosScreen.route) {
            VideosScreen(
                goToVideoScreen = { id ->
                    navController.navigate(Routes.VideoScreen.route + "/$id")
                }
            )
        }

        composable(
            route = Routes.VideoScreen.route + "/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getLong("id")
            id?.let {
                VideoScreen(
                    id = id,
                    navigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}
