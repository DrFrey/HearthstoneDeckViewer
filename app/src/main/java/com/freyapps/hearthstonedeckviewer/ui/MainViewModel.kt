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

    fun updateDecksIfNeeded() {
        Log.d(TAG, "updateDecksIfNeeded triggered")
        viewModelScope.launch {
            val currentTime = System.currentTimeMillis()

            if (currentTime - prefs.getLong(
                    HUNTER_DECKS_LAST_UPDATED,
                    0
                ) > UPDATE_INTERVAL
            ) refreshTopDecksByClass(HUNTER)

            if (currentTime - prefs.getLong(
                    WARRIOR_DECKS_LAST_UPDATED,
                    0
                ) > UPDATE_INTERVAL
            ) refreshTopDecksByClass(WARRIOR)

            if (currentTime - prefs.getLong(
                    DRUID_DECKS_LAST_UPDATED,
                    0
                ) > UPDATE_INTERVAL
            ) refreshTopDecksByClass(DRUID)

            if (currentTime - prefs.getLong(
                    MAGE_DECKS_LAST_UPDATED,
                    0
                ) > UPDATE_INTERVAL
            ) refreshTopDecksByClass(MAGE)

            if (currentTime - prefs.getLong(
                    SHAMAN_DECKS_LAST_UPDATED,
                    0
                ) > UPDATE_INTERVAL
            ) refreshTopDecksByClass(SHAMAN)

            if (currentTime - prefs.getLong(
                    PRIEST_DECKS_LAST_UPDATED,
                    0
                ) > UPDATE_INTERVAL
            ) refreshTopDecksByClass(PRIEST)

            if (currentTime - prefs.getLong(
                    PALADIN_DECKS_LAST_UPDATED,
                    0
                ) > UPDATE_INTERVAL
            ) refreshTopDecksByClass(PALADIN)

            if (currentTime - prefs.getLong(
                    DEMONHUNTER_DECKS_LAST_UPDATED,
                    0
                ) > UPDATE_INTERVAL
            ) refreshTopDecksByClass(DEMONHUNTER)

            if (currentTime - prefs.getLong(
                    ROGUE_DECKS_LAST_UPDATED,
                    0
                ) > UPDATE_INTERVAL
            ) refreshTopDecksByClass(ROGUE)

            if (currentTime - prefs.getLong(
                    WARLOCK_DECKS_LAST_UPDATED,
                    0
                ) > UPDATE_INTERVAL
            ) refreshTopDecksByClass(WARLOCK)
        }
    }

    fun refreshTopDecksByClass(hearthstoneClass: HearthstoneClass) {
        viewModelScope.launch {
            isLoading = true
            decksRepository.refreshTopDecksByClass(hearthstoneClass).collect { result ->
                when (result) {
                    is Result.Error -> {
                        error = result.message.toString()
                    }
                    is Result.Success -> {
                        val updated = System.currentTimeMillis()
                        prefs.edit().also {
                            when (hearthstoneClass) {
                                HUNTER -> it.putLong(HUNTER_DECKS_LAST_UPDATED, updated)
                                MAGE -> it.putLong(MAGE_DECKS_LAST_UPDATED, updated)
                                WARRIOR -> it.putLong(WARRIOR_DECKS_LAST_UPDATED, updated)
                                DRUID -> it.putLong(DRUID_DECKS_LAST_UPDATED, updated)
                                ROGUE -> it.putLong(ROGUE_DECKS_LAST_UPDATED, updated)
                                SHAMAN -> it.putLong(SHAMAN_DECKS_LAST_UPDATED, updated)
                                DEMONHUNTER -> it.putLong(DEMONHUNTER_DECKS_LAST_UPDATED, updated)
                                PRIEST -> it.putLong(PRIEST_DECKS_LAST_UPDATED, updated)
                                PALADIN -> it.putLong(PALADIN_DECKS_LAST_UPDATED, updated)
                                WARLOCK -> it.putLong(WARLOCK_DECKS_LAST_UPDATED, updated)
                                UNKNOWN -> {}
                            }
                        }.apply()
                        Log.d(TAG, "updated decks for $hearthstoneClass")
                    }
                    else -> {}
                }
            }
            isLoading = false
        }
    }

    private suspend fun getBlizzardAccess() {
        if (isTokenExpired()) {
            decksRepository.refreshBlizzardAccessToken().collect { result ->
                when (result) {
                    is Result.Success -> {
                        prefs.edit().also {
                            it.putString(ACCESS_TOKEN_KEY, result.data?.accessToken ?: "")
                            val tokenExpiry =
                                System.currentTimeMillis() + (result.data?.expiresIn ?: 0)
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

        private const val UPDATE_INTERVAL = 604_800_000 // 7 days in milliseconds

        private const val HUNTER_DECKS_LAST_UPDATED = "hunter_decks_last_updated"
        private const val MAGE_DECKS_LAST_UPDATED = "mage_decks_last_updated"
        private const val WARRIOR_DECKS_LAST_UPDATED = "warrior_decks_last_updated"
        private const val DRUID_DECKS_LAST_UPDATED = "druid_decks_last_updated"
        private const val ROGUE_DECKS_LAST_UPDATED = "shaman_decks_last_updated"
        private const val SHAMAN_DECKS_LAST_UPDATED = "shaman_decks_last_updated"
        private const val DEMONHUNTER_DECKS_LAST_UPDATED = "demonhunter_decks_last_updated"
        private const val PRIEST_DECKS_LAST_UPDATED = "priest_decks_last_updated"
        private const val PALADIN_DECKS_LAST_UPDATED = "paladin_decks_last_updated"
        private const val WARLOCK_DECKS_LAST_UPDATED = "warlock_decks_last_updated"
    }
}