package ir.kodato.file.core.navigation

import ir.kodato.file.core.util.Constants

sealed class NavScreen(val route: String) {

    data object FilesScreen : NavScreen("filesScreen")

    data object DirectoryScreen : NavScreen("directoryScreen/{${Constants.DIRECTORY_NAME_KEY}}") {
        fun passDirectoryName(
            directoryName: String
        ): String {
            return "directoryScreen/$directoryName"
        }
    }

    data object FileScreen : NavScreen("fileScreen/{${Constants.FILE_NAME_KEY}}") {
        fun passFileName(
            fileName: String
        ): String {
            return "fileScreen/$fileName"
        }
    }

    data object SettingsScreen : NavScreen("settingsScreen")
}