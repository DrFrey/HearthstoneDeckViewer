package com.freyapps.hearthstonedeckviewer.data.models.remote

import com.google.gson.annotations.SerializedName

data class Hero(
    @SerializedName("artistName")
    val artistName: Any?,
    @SerializedName("cardSetId")
    val cardSetId: Int?,
    @SerializedName("cardTypeId")
    val cardTypeId: Int?,
    @SerializedName("childIds")
    val childIds: List<Int>?,
    @SerializedName("classId")
    val classId: Int?,
    @SerializedName("collectible")
    val collectible: Int?,
    @SerializedName("cropImage")
    val cropImage: String?,
    @SerializedName("flavorText")
    val flavorText: String?,
    @SerializedName("health")
    val health: Int?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("imageGold")
    val imageGold: String?,
    @SerializedName("manaCost")
    val manaCost: Int?,
    @SerializedName("multiClassIds")
    val multiClassIds: List<Any>?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("rarityId")
    val rarityId: Int?,
    @SerializedName("slug")
    val slug: String?,
    @SerializedName("text")
    val text: String?
)
