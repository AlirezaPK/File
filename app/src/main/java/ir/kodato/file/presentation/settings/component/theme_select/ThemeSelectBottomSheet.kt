package ir.kodato.file.presentation.settings.component.theme_select

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import ir.kodato.file.core.presentation.main.MainEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeSelectBottomSheet(
    bottomSheetState: SheetState,
    themeState: String,
    onDismissRequest: () -> Unit,
    onEvent: (MainEvent) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = bottomSheetState
    ) {
        BottomSheetContent(
            themeState = themeState,
            onEvent = onEvent
        )
    }
}