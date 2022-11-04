package com.freyapps.hearthstonedeckviewer.data.remote

import com.freyapps.hearthstonedeckviewer.data.models.remote.Card
import com.freyapps.hearthstonedeckviewer.data.models.remote.CardResponse
import com.freyapps.hearthstonedeckviewer.data.models.remote.Deck
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val LOCALE = "en_US"

interface BlizzardApiService {

    @GET("cards/")
    suspend fun getCards(
        @Query("access_token")
        accessToken : String,
        @Query("locale")
        locale : String = LOCALE
    ) : Response<CardResponse>

    @GET("cards/{slug}")
    suspend fun getCard(
        @Path("slug")
        slug : String,
        @Query("access_token")
        accessToken : String,
        @Query("locale")
        locale : String = LOCALE
    ) : Response<Card>

    @GET("deck/")
    suspend fun getDeck(
        @Query("access_token")
        accessToken : String,
        @Query("code")
        code : String,
        @Query("locale")
        locale : String = LOCALE
    ) : Response<Deck>
}