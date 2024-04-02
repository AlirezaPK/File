package ir.kodato.file.presentation.settings.component.theme_select

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import ir.kodato.file.core.presentation.main.MainEvent
import ir.kodato.file.core.util.Themes

@Composable
fun BottomSheetContent(
    themeState: String,
    onEvent: (MainEvent) -> Unit
) {

    val themes = Themes.getList()
    val (selectedTheme: String, onOptionSelected: (String) -> Unit) = remember {
        mutableStateOf(
            themes.first {
                it == themeState
            }
        )
    }

    Column {
        themes.forEach { theme ->
            ThemeRadioButtonItem(
                theme = theme,
                selectedTheme = selectedTheme,
                onEvent = onEvent,
                onOptionSelected = onOptionSelected
            )
        }
    }
}