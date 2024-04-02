package ir.kodato.file.presentation.home.files

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import ir.kodato.file.presentation.home.core.FilesSharedEvent
import ir.kodato.file.presentation.home.core.FilesSharedState
import ir.kodato.file.presentation.home.files.component.FilesContent
import ir.kodato.file.presentation.home.files.component.FilesTopAppBar

@Composable
fun FilesScreen(
    navController: NavHostController,
    filesSharedState: FilesSharedState,
    onEvent: (FilesSharedEvent) -> Unit
) {

    onEvent(FilesSharedEvent.ParentDirectoryChange(null))

    Scaffold(
        topBar = {
            FilesTopAppBar(navController, onEvent)
        }
    ) { paddingValues ->

        FilesContent(
            paddingValues,
            navController,
            filesSharedState,
            onEvent
        )
    }
}