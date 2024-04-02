package ir.kodato.file.data.repository

import android.app.Application
import android.net.Uri
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKeys
import ir.kodato.file.core.util.SortBy
import ir.kodato.file.core.util.getFileNameFromUri
import ir.kodato.file.domain.repository.FileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import javax.inject.Inject

class FileRepositoryImpl @Inject constructor(
    private val appContext: Application
) : FileRepository {

    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

    override suspend fun getFiles(parentDirectory: String?, sortBy: SortBy): Flow<List<File>> {
        return flow {
            val baseDir = getBaseDirectory(parentDirectory).listFiles()

            val directories = baseDir?.filter { it.isDirectory }?.toMutableList() ?: mutableListOf()
            val files = baseDir?.filterNot { it.isDirectory }?.toMutableList() ?: mutableListOf()

            val sortedDirectories = when (sortBy) {
                SortBy.NAME_ASC -> directories.sortedBy { it.name }
                SortBy.NAME_DESC -> directories.sortedByDescending { it.name }
            }

            val sortedFiles = when (sortBy) {
                SortBy.NAME_ASC -> files.sortedBy { it.name }
                SortBy.NAME_DESC -> files.sortedByDescending { it.name }
            }

            emit(sortedDirectories + sortedFiles)
        }
    }

    override fun addDirectory(parentDirectory: String?, directoryName: String) {
        val baseDir = getBaseDirectory(parentDirectory)

        val directory = File(baseDir, directoryName)
        directory.mkdir()
    }

    override fun renameDirectory(
        parentDirectory: String?,
        directoryName: String,
        newDirectoryName: String
    ) {
        val baseDir = getBaseDirectory(parentDirectory)

        val oldDirectory = File(baseDir, directoryName)
        val newDirectory = File(baseDir, newDirectoryName)

        oldDirectory.renameTo(newDirectory)
    }

    override fun deleteFile(parentDirectory: String?, fileName: String) {
        val baseDir = getBaseDirectory(parentDirectory)

        val file = File(baseDir, fileName)
        file.delete()
    }

    override fun renameFile(parentDirectory: String?, fileName: String, newFileName: String) {
        val decryptedFile = decryptFile(parentDirectory, fileName)

        val newFile = File(decryptedFile.parentFile, newFileName.removeSuffix(".enc"))
        decryptedFile.renameTo(newFile)

        encryptFile(parentDirectory, newFile)

        deleteFile(parentDirectory, fileName)
    }

    override fun encryptFile(parentDirectory: String?, uri: Uri) {
        val originalFilename = getFileNameFromUri(appContext, uri)
        val fileName = "$originalFilename.enc"

        val baseDir = getBaseDirectory(parentDirectory)

        val encryptedFile = EncryptedFile.Builder(
            File(baseDir, fileName),
            appContext,
            masterKeyAlias,
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
        ).build()

        val inputStream = appContext.contentResolver.openInputStream(uri)
        val outputStream = encryptedFile.openFileOutput()

        inputStream?.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }
    }

    override fun decryptFile(parentDirectory: String?, encryptedFileName: String): File {

        val baseDir = getBaseDirectory(parentDirectory)

        val encryptedFile = EncryptedFile.Builder(
            File(baseDir, encryptedFileName),
            appContext,
            masterKeyAlias,
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
        ).build()

        val decryptedFileName = encryptedFileName.removeSuffix(".enc")
        val decryptedFile = File(appContext.cacheDir, decryptedFileName)

        val inputStream = encryptedFile.openFileInput()
        val outputStream = FileOutputStream(decryptedFile)

        inputStream.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }

        return decryptedFile
    }

    private fun encryptFile(parentDirectory: String?, file: File) {
        val originalFilename = file.name
        val fileName = "$originalFilename.enc"

        val baseDir = getBaseDirectory(parentDirectory)

        val encryptedFile = EncryptedFile.Builder(
            File(baseDir, fileName),
            appContext,
            masterKeyAlias,
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
        ).build()

        val inputStream = FileInputStream(file)
        val outputStream = encryptedFile.openFileOutput()

        inputStream.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }
    }

    private fun getBaseDirectory(parentDirectory: String?): File {
        var baseDir = appContext.filesDir
        if (!parentDirectory.isNullOrEmpty()) {
            baseDir = File(baseDir, parentDirectory)
        }
        return baseDir
    }
}