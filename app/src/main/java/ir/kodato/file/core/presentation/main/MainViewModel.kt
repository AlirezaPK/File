package ir.kodato.file.core.presentation.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.kodato.file.core.domain.repository.MainRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _theme = mutableStateOf("")
    val theme: State<String> = _theme

    init {
        readTheme()
    }

    fun onEvent(event: MainEvent) {
        when (event) {
            is MainEvent.ChangeTheme -> changeTheme(event.theme)
        }
    }

    private fun readTheme() {
        viewModelScope.launch {
            mainRepository.readTheme().collect {
                _theme.value = it
            }
        }
    }

    private fun changeTheme(theme: String) {
        viewModelScope.launch {
            mainRepository.saveTheme(theme)
        }
    }
}