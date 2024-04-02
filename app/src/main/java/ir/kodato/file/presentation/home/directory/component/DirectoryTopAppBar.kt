package ir.kodato.file.presentation.home.directory.component

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import ir.kodato.file.presentation.home.core.FilesSharedEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DirectoryTopAppBar(
    navController: NavHostController,
    directoryName: String,
    onEvent: (FilesSharedEvent) -> Unit
) {

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.OpenMultipleDocuments()) { uris ->
            uris.forEach { uri ->
                onEvent(FilesSharedEvent.EncryptFile(uri))
            }
        }

    TopAppBar(
        title = {
            Text(text = directoryName)
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        actions = {

            IconButton(
                onClick = {
                    onEvent(FilesSharedEvent.SortByChange)
                }
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.Sort,
                    contentDescription = "Sort By"
                )
            }

            IconButton(
                onClick = {
                    launcher.launch(
                        arrayOf(
                            "application/pdf",
                            "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
                        )
                    )
                }
            ) {
                Icon(
                    Icons.Default.Add,
                    contentDescription = "Add File"
                )
            }
        }
    )
}