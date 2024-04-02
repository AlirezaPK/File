package ir.kodato.file.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ir.kodato.file.core.presentation.main.MainViewModel
import ir.kodato.file.core.util.Constants
import ir.kodato.file.presentation.home.files.FilesScreen
import ir.kodato.file.presentation.home.core.FilesSharedViewModel
import ir.kodato.file.presentation.home.directory.DirectoryScreen
import ir.kodato.file.presentation.home.file.FileScreen
import ir.kodato.file.presentation.settings.SettingsScreen

@Composable
fun NavGraph() {

    val navController = rememberNavController()

    val filesSharedViewModel = hiltViewModel<FilesSharedViewModel>()
    val filesSharedState by filesSharedViewModel.state.collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
        startDestination = NavScreen.FilesScreen.route
    ) {

        composable(NavScreen.FilesScreen.route) {
            FilesScreen(
                navController,
                filesSharedState,
                filesSharedViewModel::onEvent
            )
        }

        composable(
            NavScreen.DirectoryScreen.route,
            arguments = listOf(
                navArgument(Constants.DIRECTORY_NAME_KEY) {
                    type = NavType.StringType
                }
            )
        ) { navBackStackEntry ->
            val directoryName =
                navBackStackEntry.arguments?.getString(Constants.DIRECTORY_NAME_KEY) ?: ""

            DirectoryScreen(
                navController,
                directoryName,
                filesSharedState,
                filesSharedViewModel::onEvent
            )
        }

        composable(
            NavScreen.FileScreen.route,
            arguments = listOf(
                navArgument(Constants.FILE_NAME_KEY) {
                    type = NavType.StringType
                }
            )
        ) { navBackStackEntry ->

            val fileName =
                navBackStackEntry.arguments?.getString(Constants.FILE_NAME_KEY) ?: ""

            FileScreen(
                navController,
                fileName,
                filesSharedState,
                filesSharedViewModel::onEvent
            )
        }

        composable(NavScreen.SettingsScreen.route) {
            val mainViewModel = hiltViewModel<MainViewModel>()
            val themeState by mainViewModel.theme

            SettingsScreen(
                navController,
                themeState = themeState,
                onEvent = mainViewModel::onEvent
            )
        }
    }
}