package com.freyapps.hearthstonedeckviewer.data.remote

import com.freyapps.hearthstonedeckviewer.data.models.remote.AccessDataRemote
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

private const val CLIENT_ID = "8a652978c8fd447e922e8ed1ec3c7170"
private const val CLIENT_SECRET = "3Nr9Ifa7V8msMosfmMp3CQtsYmOTrNnv"

interface BlizzardAuthService {

    @POST("oauth/token")
    suspend fun getToken(
        @Query("client_id")
        clientId : String = CLIENT_ID,
        @Query("client_secret")
        clientSecret : String = CLIENT_SECRET,
        @Query("grant_type")
        clientCredentials : String = "client_credentials",
        @Query("scope")
        scope : String = "openid"
    ) : Response<AccessDataRemote>
}