package com.freyapps.hearthstonedeckviewer.data.models.remote

import com.google.gson.annotations.SerializedName

data class Deck(
    @SerializedName("cardCount")
    val cardCount: Int?,

    @SerializedName("cards")
    val cards: List<Card>?,

    @SerializedName("class")
    val classX: ClassX?,

    @SerializedName("deckCode")
    val deckCode: String?,

    @SerializedName("format")
    val format: String?,

    @SerializedName("hero")
    val hero: Hero?,

    @SerializedName("heroPower")
    val heroPower: HeroPower?,

    @SerializedName("version")
    val version: Int?
)
