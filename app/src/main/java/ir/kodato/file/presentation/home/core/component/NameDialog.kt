package ir.kodato.file.presentation.home.core.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import ir.kodato.file.core.util.getExtension
import ir.kodato.file.presentation.home.core.FilesSharedEvent
import ir.kodato.file.presentation.home.core.FilesSharedState

@Composable
fun NameDialog(
    filesSharedState: FilesSharedState,
    onEvent: (FilesSharedEvent) -> Unit
) {

    AlertDialog(
        onDismissRequest = {
            onEvent(FilesSharedEvent.NameDialogChange(false))
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (filesSharedState.selectedFile == null) {
                        onEvent(FilesSharedEvent.AddDirectory(filesSharedState.fileName))
                    } else {
                        if (filesSharedState.selectedFile.isDirectory) {
                            onEvent(
                                FilesSharedEvent.RenameDirectory(
                                    filesSharedState.selectedFile.name,
                                    filesSharedState.fileName
                                )
                            )
                        } else {
                            val extension = getExtension(filesSharedState.selectedFile.name)
                            onEvent(
                                FilesSharedEvent.RenameFile(
                                    filesSharedState.selectedFile.name,
                                    filesSharedState.fileName + extension
                                )
                            )
                        }
                    }
                    onEvent(FilesSharedEvent.FileNameChange(""))
                    onEvent(FilesSharedEvent.NameDialogChange(false))
                }
            ) {
                Text(text = "OK")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onEvent(FilesSharedEvent.FileNameChange(""))
                    onEvent(FilesSharedEvent.NameDialogChange(false))
                }
            ) {
                Text(text = "Cancel")
            }
        },
        title = { Text(text = "New Name") },
        text = {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = filesSharedState.fileName,
                onValueChange = { fileName ->
                    onEvent(FilesSharedEvent.FileNameChange(fileName))
                },
                placeholder = {
                    Text(
                        text = filesSharedState.selectedFile?.name
                            ?.removeSuffix(".docx.enc")
                            ?.removeSuffix(".pdf.enc")
                            ?: ""
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                )
            )
        }
    )
}