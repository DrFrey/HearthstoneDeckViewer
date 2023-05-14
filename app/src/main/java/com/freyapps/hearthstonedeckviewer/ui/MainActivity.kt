package com.freyapps.hearthstonedeckviewer.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import com.freyapps.hearthstonedeckviewer.ui.theme.HearthstoneDeckViewerTheme
import com.freyapps.hearthstonedeckviewer.ui.top_decks_screen.TopDecksScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.updateDecksIfNeeded()

        setContent {
            HearthstoneDeckViewerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    TopDecksScreen(viewModel = viewModel)
                }
            }
        }
    }
}