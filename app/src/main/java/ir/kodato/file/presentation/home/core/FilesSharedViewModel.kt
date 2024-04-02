package ir.kodato.file.presentation.home.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.kodato.file.core.util.SortBy
import ir.kodato.file.domain.repository.FileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilesSharedViewModel @Inject constructor(
    private val fileRepository: FileRepository
) : ViewModel() {

    private val _state = MutableStateFlow(FilesSharedState())
    val state = _state.asStateFlow()

    init {
        getFiles(_state.value.sortBy)
    }

    fun onEvent(event: FilesSharedEvent) {
        when (event) {

            is FilesSharedEvent.AddDirectory -> {
                fileRepository.addDirectory(_state.value.parentDirectory, event.directoryName)
                getFiles(_state.value.sortBy)
            }

            is FilesSharedEvent.RenameDirectory -> {
                fileRepository.renameDirectory(
                    _state.value.parentDirectory,
                    event.directoryName,
                    event.newDirectoryName
                )
                getFiles(_state.value.sortBy)
            }

            is FilesSharedEvent.DeleteFile -> {
                fileRepository.deleteFile(_state.value.parentDirectory, event.fileName)
                getFiles(_state.value.sortBy)
            }

            is FilesSharedEvent.RenameFile -> {
                fileRepository.renameFile(
                    _state.value.parentDirectory,
                    event.fileName,
                    event.newFileName
                )
                getFiles(_state.value.sortBy)
            }

            is FilesSharedEvent.EncryptFile -> {
                fileRepository.encryptFile(_state.value.parentDirectory, event.uri)
                getFiles(_state.value.sortBy)
            }

            is FilesSharedEvent.DecryptFile -> {
                val decryptedFile =
                    fileRepository.decryptFile(
                        _state.value.parentDirectory,
                        event.encryptedFileName
                    )

                _state.value = _state.value.copy(file = decryptedFile)
            }

            is FilesSharedEvent.FileNameChange -> {
                _state.value = _state.value.copy(fileName = event.newFileName)
            }

            is FilesSharedEvent.NameDialogChange -> {
                _state.value = _state.value.copy(
                    isNameDialogOpen = event.isOpen,
                    selectedFile = event.selectedFile
                )
            }

            is FilesSharedEvent.SortByChange -> {
                if (_state.value.sortBy == SortBy.NAME_ASC) {
                    getFiles(SortBy.NAME_DESC)
                } else {
                    getFiles(SortBy.NAME_ASC)
                }
            }

            is FilesSharedEvent.ParentDirectoryChange -> {
                _state.value = _state.value.copy(parentDirectory = event.parentDirectoryName)
                getFiles(_state.value.sortBy)
            }
        }
    }

    private fun getFiles(sortBy: SortBy) {
        viewModelScope.launch {
            _state.value = _state.value.copy(sortBy = sortBy)

            fileRepository.getFiles(_state.value.parentDirectory, sortBy).collect {

                _state.value = _state.value.copy(files = it)
            }
        }
    }
}