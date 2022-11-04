package com.freyapps.hearthstonedeckviewer.data.models.remote

import com.google.gson.annotations.SerializedName

data class Duels(
    @SerializedName("constructed")
    val constructed: Boolean?,
    @SerializedName("relevant")
    val relevant: Boolean?
)
