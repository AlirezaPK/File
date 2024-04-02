package ir.kodato.file.presentation.home.directory

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import ir.kodato.file.presentation.home.core.FilesSharedEvent
import ir.kodato.file.presentation.home.core.FilesSharedState
import ir.kodato.file.presentation.home.directory.component.DirectoryContent
import ir.kodato.file.presentation.home.directory.component.DirectoryTopAppBar

@Composable
fun DirectoryScreen(
    navController: NavHostController,
    directoryName: String,
    filesSharedState: FilesSharedState,
    onEvent: (FilesSharedEvent) -> Unit
) {

    onEvent(FilesSharedEvent.ParentDirectoryChange(directoryName))

    Scaffold(
        topBar = {
            DirectoryTopAppBar(navController, directoryName, onEvent)
        }
    ) { paddingValues ->

        DirectoryContent(
            paddingValues,
            navController,
            filesSharedState,
            onEvent
        )
    }
}