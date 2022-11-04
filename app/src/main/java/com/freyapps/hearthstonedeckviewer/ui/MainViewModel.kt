package com.freyapps.hearthstonedeckviewer.ui

import android.content.Context
import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.freyapps.hearthstonedeckviewer.data.models.local.HearthstoneClass
import com.freyapps.hearthstonedeckviewer.data.models.local.HearthstoneClass.*
import com.freyapps.hearthstonedeckviewer.data.repository.DecksRepository
import com.freyapps.hearthstonedeckviewer.data.repository.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val decksRepository: DecksRepository,
    @ApplicationContext context: Context
) : ViewModel() {

    private val prefs = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)

    var hunterDecks = decksRepository.localTopDecksByClass(HUNTER)
        private set
    var warriorDecks = decksRepository.localTopDecksByClass(WARRIOR)
        private set
    var druidDecks = decksRepository.localTopDecksByClass(DRUID)
        private set
    var rogueDecks = decksRepository.localTopDecksByClass(ROGUE)
        private set
    var paladinDecks = decksRepository.localTopDecksByClass(PALADIN)
        private set
    var priestDecks = decksRepository.localTopDecksByClass(PRIEST)
        private set
    var shamanDecks = decksRepository.localTopDecksByClass(SHAMAN)
        private set
    var dhDecks = decksRepository.localTopDecksByClass(DEMONHUNTER)
        private set
    var warlockDecks = decksRepository.localTopDecksByClass(WARLOCK)
        private set
    var mageDecks = decksRepository.localTopDecksByClass(MAGE)
        private set

    var isLoading by mutableStateOf(false)
        private set

    var error by mutableStateOf("")
        private set

    init {
        viewModelScope.launch {
            Log.d(TAG, "Refreshing Blizzard Auth token")
            getBlizzardAccess()
        }
    }

    fun retrieveAllTopDecks() {
        viewModelScope.launch {
            isLoading = true
            Log.d(TAG, "isLoading = $isLoading")
            decksRepository.retrieveAllTopDecks()
            isLoading = false
            Log.d(TAG, "isLoading = $isLoading")
        }
    }

    fun refreshTopDecksByClass(hearthstoneClass: HearthstoneClass) {
        viewModelScope.launch {
            isLoading = true
            decksRepository.refreshTopDecksByClass(hearthstoneClass)
            isLoading = false
        }
    }

    private suspend fun getBlizzardAccess() {
        if (isTokenExpired()) {
            decksRepository.refreshBlizzardAccessToken().collect { result ->
                when(result) {
                    is Result.Success -> {
                        prefs.edit().also {
                            it.putString(ACCESS_TOKEN_KEY, result.data?.accessToken ?: "")
                            val tokenExpiry = System.currentTimeMillis() + (result.data?.expiresIn ?: 0)
                            it.putLong(ACCESS_TOKEN_EXPIRY, tokenExpiry)
                        }.apply()
                    }
                    is Result.Error -> {
                        error = result.message.toString()
                    }
                    is Result.Loading -> {}
                }
            }
        }
    }

    fun clearErrorMessage() {
        error = ""
    }

    private fun isTokenExpired() =
        System.currentTimeMillis() > prefs.getLong(ACCESS_TOKEN_EXPIRY, 0)

    companion object {
        private const val TAG = "MainViewModel"
        private const val SHARED_PREFS = "DeckViewer.preferences"
        private const val ACCESS_TOKEN_KEY = "access_token"
        private const val ACCESS_TOKEN_EXPIRY = "token_expiry"
    }
}