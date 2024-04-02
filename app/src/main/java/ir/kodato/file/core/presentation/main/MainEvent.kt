package ir.kodato.file.core.presentation.main

sealed interface MainEvent {

    data class ChangeTheme(val theme: String) : MainEvent
}