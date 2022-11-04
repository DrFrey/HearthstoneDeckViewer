package com.freyapps.hearthstonedeckviewer.data.models.remote

import com.google.gson.annotations.SerializedName

data class AccessDataRemote(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("expires_in")
    val expiresIn: Int,
    @SerializedName("scope")
    val scope: String,
    @SerializedName("token_type")
    val tokenType: String
)
