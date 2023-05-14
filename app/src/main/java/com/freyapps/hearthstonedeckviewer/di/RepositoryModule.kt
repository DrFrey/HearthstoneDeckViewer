package com.freyapps.hearthstonedeckviewer.di

import com.freyapps.hearthstonedeckviewer.data.remote.DataSourceRemote
import com.freyapps.hearthstonedeckviewer.data.repository.BlizzardDeckRepositoryImpl
import com.freyapps.hearthstonedeckviewer.data.repository.ManacostDecksRepositoryImpl
import com.freyapps.hearthstonedeckviewer.data.storage.DataSourceLocal
import com.freyapps.hearthstonedeckviewer.domain.repositories.BlizzardDeckRepository
import com.freyapps.hearthstonedeckviewer.domain.repositories.ManacostDecksRepository
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
    fun provideManacostDecksRepository(
        dataSourceRemote: DataSourceRemote,
        dataSourceLocal: DataSourceLocal
    ): ManacostDecksRepository =
        ManacostDecksRepositoryImpl(dataSourceRemote, dataSourceLocal)

    @Singleton
    @Provides
    fun provideBlizzardDeckRepository(
        dataSourceRemote: DataSourceRemote,
        dataSourceLocal: DataSourceLocal
    ): BlizzardDeckRepository = BlizzardDeckRepositoryImpl(dataSourceRemote, dataSourceLocal)
}