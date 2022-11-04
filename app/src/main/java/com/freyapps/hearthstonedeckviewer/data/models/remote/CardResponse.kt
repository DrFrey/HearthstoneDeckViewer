package com.freyapps.hearthstonedeckviewer.data.models.remote

import com.google.gson.annotations.SerializedName

data class CardResponse(
    @SerializedName("cardCount")
    val cardCount: Int,
    @SerializedName("cards")
    val cards: List<Card>,
    @SerializedName("page")
    val page: Int,
    @SerializedName("pageCount")
    val pageCount: Int
)
