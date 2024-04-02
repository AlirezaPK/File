package ir.kodato.file.core.data.manager

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import ir.kodato.file.core.domain.manager.ThemeManager
import ir.kodato.file.core.util.Constants
import ir.kodato.file.core.util.Themes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore by preferencesDataStore(
    name = Constants.APP_SETTINGS
)

private object PreferencesKeys {
    val THEME_KEY = stringPreferencesKey(Constants.THEME_KEY)
}

class ThemeManagerImpl @Inject constructor(
    private val appContext: Application
) : ThemeManager {

    override suspend fun saveTheme(theme: String) {
        appContext.dataStore.edit {
            it[PreferencesKeys.THEME_KEY] = theme
        }
    }

    override suspend fun readTheme(): Flow<String> {
        return appContext.dataStore.data.map {
            it[PreferencesKeys.THEME_KEY] ?: Themes.SYSTEM.title
        }
    }
}