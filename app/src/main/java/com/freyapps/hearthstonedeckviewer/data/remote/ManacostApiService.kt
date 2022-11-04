package com.freyapps.hearthstonedeckviewer.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ManacostApiService {

    @GET("hearthstone-top-decks/")
    suspend fun getTopDecksByClass(
        @Query("tag_top_deck_legend")
        hearthstoneClass: String,
        @Query("pagination")
        page: Int
    ) : Response<String>
}