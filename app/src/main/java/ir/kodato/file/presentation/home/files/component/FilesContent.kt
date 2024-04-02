package ir.kodato.file.presentation.home.files.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ir.kodato.file.core.navigation.NavScreen
import ir.kodato.file.presentation.home.core.FilesSharedEvent
import ir.kodato.file.presentation.home.core.FilesSharedState
import ir.kodato.file.presentation.home.core.component.NameDialog

@Composable
fun FilesContent(
    paddingValues: PaddingValues,
    navController: NavHostController,
    filesSharedState: FilesSharedState,
    onEvent: (FilesSharedEvent) -> Unit
) {

    LazyColumn(
        modifier = Modifier.padding(paddingValues),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(filesSharedState.files) { file ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        if (file.isDirectory) {
                            navController.navigate(
                                NavScreen.DirectoryScreen.passDirectoryName(
                                    file.name
                                )
                            )
                        } else {
                            navController.navigate(
                                NavScreen.FileScreen.passFileName(
                                    file.name
                                )
                            )
                        }
                    }
                    .padding(start = 16.dp, end = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    if (file.isDirectory) {
                        Icon(
                            imageVector = Icons.Default.Folder,
                            contentDescription = "Directory"
                        )
                    }
                    Text(
                        text = file.name.removeSuffix(".enc"),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Row {
                    IconButton(
                        onClick = {
                            onEvent(FilesSharedEvent.NameDialogChange(true, file))
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Name"
                        )
                    }

                    IconButton(
                        onClick = {
                            onEvent(FilesSharedEvent.DeleteFile(file.name))
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete"
                        )
                    }
                }
            }
        }
    }

    if (filesSharedState.isNameDialogOpen) {
        NameDialog(filesSharedState, onEvent)
    }
}