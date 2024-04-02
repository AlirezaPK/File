package ir.kodato.file.core.domain.manager

import kotlinx.coroutines.flow.Flow

interface ThemeManager {

    suspend fun saveTheme(theme: String)

    suspend fun readTheme(): Flow<String>
}