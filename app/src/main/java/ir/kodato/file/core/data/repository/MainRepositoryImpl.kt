package ir.kodato.file.core.data.repository

import ir.kodato.file.core.domain.manager.ThemeManager
import ir.kodato.file.core.domain.repository.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val themeManager: ThemeManager
) : MainRepository {

    override suspend fun saveTheme(theme: String) {
        themeManager.saveTheme(theme)
    }

    override suspend fun readTheme(): Flow<String> {
        return themeManager.readTheme()
    }
}