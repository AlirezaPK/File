package ir.kodato.file.domain.repository

import android.net.Uri
import ir.kodato.file.core.util.SortBy
import kotlinx.coroutines.flow.Flow
import java.io.File

interface FileRepository {

    suspend fun getFiles(parentDirectory: String?, sortBy: SortBy): Flow<List<File>>

    fun addDirectory(parentDirectory: String?, directoryName: String)

    fun renameDirectory(parentDirectory: String?, directoryName: String, newDirectoryName: String)

    fun deleteFile(parentDirectory: String?, fileName: String)

    fun renameFile(parentDirectory: String?, fileName: String, newFileName: String)

    fun encryptFile(parentDirectory: String?, uri: Uri)

    fun decryptFile(parentDirectory: String?, encryptedFileName: String): File
}