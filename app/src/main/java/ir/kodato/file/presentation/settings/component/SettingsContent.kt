package ir.kodato.file.presentation.settings.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ir.kodato.file.core.presentation.main.MainEvent
import ir.kodato.file.presentation.settings.component.theme_select.ThemeSelectBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsContent(
    paddingValues: PaddingValues,
    themeState: String,
    onEvent: (MainEvent) -> Unit
) {

    val bottomSheetState = rememberModalBottomSheetState()
    var isSheetOpen by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .clickable {
                    isSheetOpen = true
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "Theme",
                style = MaterialTheme.typography.bodyLarge
            )

            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = themeState,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }

    if (isSheetOpen) {
        ThemeSelectBottomSheet(
            bottomSheetState = bottomSheetState,
            themeState = themeState,
            onDismissRequest = { isSheetOpen = false },
            onEvent = onEvent
        )
    }
}