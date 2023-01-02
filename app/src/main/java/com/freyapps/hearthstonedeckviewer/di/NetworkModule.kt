package com.freyapps.hearthstonedeckviewer.di

import com.freyapps.hearthstonedeckviewer.BuildConfig
import com.freyapps.hearthstonedeckviewer.data.remote.BlizzardApiService
import com.freyapps.hearthstonedeckviewer.data.remote.BlizzardAuthService
import com.freyapps.hearthstonedeckviewer.data.remote.ManacostApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val MANACOST_BASE_URL = "http://hs-manacost.ru/"
    private const val BLIZZARD_BASE_URL = "https://us.api.blizzard.com/hearthstone/"
    private const val BLIZZARD_AUTH_URL = "https://eu.battle.net/"

    @Singleton
    @Provides
    fun provideManacostApiService(okHttpClient: OkHttpClient) : ManacostApiService {
        return Retrofit.Builder()
            .baseUrl(MANACOST_BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(ManacostApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideBlizzardAuthService(okHttpClient: OkHttpClient) : BlizzardAuthService {
        return Retrofit.Builder()
            .baseUrl(BLIZZARD_AUTH_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(BlizzardAuthService::class.java)
    }

    @Singleton
    @Provides
    fun provideBlizzardApiService(okHttpClient: OkHttpClient) : BlizzardApiService {
        return Retrofit.Builder()
            .baseUrl(BLIZZARD_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(BlizzardApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient().newBuilder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            okHttpClientBuilder.addInterceptor(
                HttpLoggingInterceptor().setLevel(
                    HttpLoggingInterceptor.Level.BASIC
                )
            )
        }
        return okHttpClientBuilder.build()
    }
}