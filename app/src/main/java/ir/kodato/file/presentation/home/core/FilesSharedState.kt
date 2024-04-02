package ir.kodato.file.presentation.home.core

import ir.kodato.file.core.util.SortBy
import java.io.File

data class FilesSharedState(
    val files: List<File> = emptyList(),
    val isNameDialogOpen: Boolean = false,
    val selectedFile: File? = null,
    val fileName: String = "",
    val sortBy: SortBy = SortBy.NAME_ASC,
    val file: File? = null,
    val parentDirectory: String? = null
)
