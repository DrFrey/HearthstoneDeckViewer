package com.freyapps.hearthstonedeckviewer.data.repository

import com.freyapps.hearthstonedeckviewer.data.remote.DataSourceRemote
import com.freyapps.hearthstonedeckviewer.data.storage.DataSourceLocal
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideDecksRepository(
        dataSourceRemote: DataSourceRemote,
        dataSourceLocal: DataSourceLocal
    ): DecksRepository =
        DecksRepositoryImpl(dataSourceRemote, dataSourceLocal)
}