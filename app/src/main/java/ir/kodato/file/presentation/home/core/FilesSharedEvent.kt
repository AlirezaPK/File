package ir.kodato.file.presentation.home.core

import android.net.Uri
import java.io.File

sealed interface FilesSharedEvent {

    data class AddDirectory(val directoryName: String) : FilesSharedEvent

    data class RenameDirectory(val directoryName: String, val newDirectoryName: String) :
        FilesSharedEvent

    data class DeleteFile(val fileName: String) : FilesSharedEvent

    data class RenameFile(val fileName: String, val newFileName: String) : FilesSharedEvent

    data class EncryptFile(val uri: Uri) : FilesSharedEvent

    data class DecryptFile(val encryptedFileName: String) : FilesSharedEvent

    data class NameDialogChange(val isOpen: Boolean, val selectedFile: File? = null) :
        FilesSharedEvent

    data class FileNameChange(val newFileName: String) : FilesSharedEvent

    data object SortByChange : FilesSharedEvent

    data class ParentDirectoryChange(val parentDirectoryName: String?) : FilesSharedEvent
}