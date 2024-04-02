package ir.kodato.file.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface MainRepository {

    suspend fun saveTheme(theme: String)

    suspend fun readTheme(): Flow<String>
}