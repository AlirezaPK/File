package ir.kodato.file.presentation.settings

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import ir.kodato.file.core.presentation.main.MainEvent
import ir.kodato.file.presentation.settings.component.SettingsContent
import ir.kodato.file.presentation.settings.component.SettingsTopAppBar

@Composable
fun SettingsScreen(
    navController: NavHostController,
    themeState: String,
    onEvent: (MainEvent) -> Unit
) {

    Scaffold(
        topBar = {
            SettingsTopAppBar(navController = navController)
        }
    ) { paddingValues ->

        SettingsContent(
            paddingValues,
            themeState,
            onEvent
        )
    }
}
