package com.freyapps.hearthstonedeckviewer.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.freyapps.hearthstonedeckviewer.ui.deck_screen.DeckScreen
import com.freyapps.hearthstonedeckviewer.ui.theme.HearthstoneDeckViewerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeckActivity : ComponentActivity() {

    private val viewModel: DeckViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val extras = intent.extras
        extras?.let {
            viewModel.getDeck(it.getString("DECK_ITEM_CODE") ?: "")
        }

        setContent {
            HearthstoneDeckViewerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    DeckScreen(viewModel)
                }
            }
        }
    }
}