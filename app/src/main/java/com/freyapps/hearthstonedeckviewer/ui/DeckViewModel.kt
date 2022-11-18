package com.freyapps.hearthstonedeckviewer.ui

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freyapps.hearthstonedeckviewer.data.models.local.CardLocal
import com.freyapps.hearthstonedeckviewer.data.models.local.DeckLocal
import com.freyapps.hearthstonedeckviewer.data.repository.DecksRepository
import com.freyapps.hearthstonedeckviewer.data.repository.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeckViewModel @Inject constructor(
    private val decksRepository: DecksRepository,
    @ApplicationContext context: Context
) : ViewModel() {

    private val prefs = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)

    var deck by mutableStateOf<DeckLocal?>(null)
        private set

    var card by mutableStateOf<CardLocal?>(null)
        private set

    var error by mutableStateOf("")
        private set

    var isLoading by mutableStateOf(false)
        private set

    var isCardLoading by mutableStateOf(false)
        private set

    fun getDeck(code: String) {
        Log.d("DeckViewModel", "deck code - $code")
        if (!isTokenExpired()) {
            viewModelScope.launch {
                decksRepository.getBlizzardDeckByCode(
                    token = prefs.getString(ACCESS_TOKEN_KEY, "") ?: "",
                    code = code
                ).collect { result ->
                    when (result) {
                        is Result.Success -> {
                            isLoading = false
                            deck = result.data
                        }
                        is Result.Error -> {
                            isLoading = false
                            error = result.message.toString()
                        }
                        is Result.Loading -> {
                            isLoading = true
                        }
                    }
                }
            }
        } else {
            viewModelScope.launch {
                decksRepository.refreshBlizzardAccessToken().collect { result ->
                    when (result) {
                        is Result.Success -> {
                            prefs.edit().also {
                                it.putString(
                                    ACCESS_TOKEN_KEY,
                                    result.data?.accessToken ?: ""
                                )
                                val tokenExpiry =
                                    System.currentTimeMillis() + (result.data?.expiresIn ?: 0)
                                it.putLong(ACCESS_TOKEN_EXPIRY, tokenExpiry)
                            }.apply()
                            getDeck(code)
                        }
                        is Result.Error -> {
                            error = result.message.toString()
                        }
                        is Result.Loading -> {}
                    }
                }
            }
        }
    }

    fun getCard(slug: String) {
        Log.d("DeckViewModel", "card slug - $slug")
        if (!isTokenExpired()) {
            viewModelScope.launch {
                decksRepository.getBlizzardCardBySlug(
                    slug = slug,
                    token = prefs.getString(ACCESS_TOKEN_KEY, "") ?: ""
                ).collect { result ->
                    when (result) {
                        is Result.Success -> {
                            isCardLoading = false
                            card = result.data
                        }
                        is Result.Error -> {
                            isCardLoading = false
                            error = result.message.toString()
                        }
                        is Result.Loading -> {
                            isCardLoading = true
                        }
                    }
                }
            }
        } else {
            viewModelScope.launch {
                decksRepository.refreshBlizzardAccessToken().collect { result ->
                    when (result) {
                        is Result.Success -> {
                            prefs.edit().also {
                                it.putString(
                                    ACCESS_TOKEN_KEY,
                                    result.data?.accessToken ?: ""
                                )
                                val tokenExpiry =
                                    System.currentTimeMillis() + (result.data?.expiresIn ?: 0)
                                it.putLong(ACCESS_TOKEN_EXPIRY, tokenExpiry)
                            }.apply()
                            getCard(slug)
                        }
                        is Result.Error -> {
                            error = result.message.toString()
                        }
                        is Result.Loading -> {}
                    }
                }
            }
        }
    }

    private fun isTokenExpired() =
        System.currentTimeMillis() > prefs.getLong(ACCESS_TOKEN_EXPIRY, 0)

    fun clearErrorMessage() {
        error = ""
    }

    companion object {
        private const val TAG = "DeckViewModel"
        private const val SHARED_PREFS = "DeckViewer.preferences"
        private const val ACCESS_TOKEN_KEY = "access_token"
        private const val ACCESS_TOKEN_EXPIRY = "token_expiry"
    }

}