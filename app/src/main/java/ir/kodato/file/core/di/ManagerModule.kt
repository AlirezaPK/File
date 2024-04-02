package ir.kodato.file.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.kodato.file.core.data.manager.ThemeManagerImpl
import ir.kodato.file.core.domain.manager.ThemeManager
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ManagerModule {

    @Binds
    @Singleton
    abstract fun bindLocalThemeManager(
        localUserManagerImpl: ThemeManagerImpl
    ): ThemeManager
}