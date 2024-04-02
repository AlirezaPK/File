package ir.kodato.file.presentation.home.file

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ireward.htmlcompose.HtmlText
import com.rajat.pdfviewer.compose.PdfRendererViewCompose
import ir.kodato.file.presentation.home.core.FilesSharedEvent
import ir.kodato.file.presentation.home.core.FilesSharedState
import org.zwobble.mammoth.DocumentConverter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FileScreen(
    navController: NavHostController,
    fileName: String,
    filesSharedState: FilesSharedState,
    onEvent: (FilesSharedEvent) -> Unit
) {

    var isFontDialogVisible by remember { mutableStateOf(false) }

    var fontSizeState by remember { mutableStateOf(14.sp) }
    var fontSizeInput by remember { mutableStateOf("") }

    onEvent(FilesSharedEvent.DecryptFile(fileName))

    filesSharedState.file?.let { file ->
        if (file.name.contains(".pdf")) {
            PdfRendererViewCompose(
                file = file,
                lifecycleOwner = LocalLifecycleOwner.current
            )
        } else {
            val docx = DocumentConverter().convertToHtml(file).value

            Column {
                TopAppBar(
                    title = {
                        Text(
                            text = fileName.removeSuffix(".enc"),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
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
                                isFontDialogVisible = true
                            }
                        ) {
                            Icon(
                                Icons.Default.FormatSize,
                                contentDescription = "Font Size"
                            )
                        }
                    }
                )

                HtmlText(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp),
                    text = docx,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Justify,
                        fontSize = fontSizeState
                    )
                )
            }

            if (isFontDialogVisible) {
                AlertDialog(
                    onDismissRequest = {
                        isFontDialogVisible = false
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                fontSizeState = fontSizeInput.toFloat().sp
                                fontSizeInput = ""
                                isFontDialogVisible = false
                            }
                        ) {
                            Text(text = "OK")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                isFontDialogVisible = false
                            }
                        ) {
                            Text(text = "Cancel")
                        }
                    },
                    title = { Text(text = "Font Size") },
                    text = {
                        OutlinedTextField(
                            modifier = Modifier.fillMaxWidth(),
                            value = fontSizeInput,
                            onValueChange = {
                                fontSizeInput = it
                            },
                            placeholder = {
                                Text(text = fontSizeState.value.toInt().toString())
                            },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            )
                        )
                    }
                )
            }
        }
    }
}