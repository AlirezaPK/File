package ir.kodato.file.presentation.settings.component.theme_select

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import ir.kodato.file.core.presentation.main.MainEvent

@Composable
fun ThemeRadioButtonItem(
    theme: String,
    selectedTheme: String,
    onEvent: (MainEvent) -> Unit,
    onOptionSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .selectable(
                selected = (theme == selectedTheme),
                onClick = {
                    onEvent(MainEvent.ChangeTheme(theme))
                    onOptionSelected(theme)
                },
                role = Role.RadioButton
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = (theme == selectedTheme),
            onClick = {
                onEvent(MainEvent.ChangeTheme(theme))
                onOptionSelected(theme)
            }
        )

        Text(
            text = theme
        )
    }
}