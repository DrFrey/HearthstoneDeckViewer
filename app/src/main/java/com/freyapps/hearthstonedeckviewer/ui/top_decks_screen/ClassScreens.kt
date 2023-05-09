package com.freyapps.hearthstonedeckviewer.ui.top_decks_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.freyapps.hearthstonedeckviewer.R
import com.freyapps.hearthstonedeckviewer.common.DAY_MONTH
import com.freyapps.hearthstonedeckviewer.common.convertLongToFormattedDate
import com.freyapps.hearthstonedeckviewer.common.shimmerBackground
import com.freyapps.hearthstonedeckviewer.data.models.local.HearthstoneClass
import com.freyapps.hearthstonedeckviewer.data.models.local.ManacostDeckInfo
import com.freyapps.hearthstonedeckviewer.ui.MainViewModel


@Composable
fun DeathknightScreen(
    viewModel: MainViewModel,
    onClick: (String) -> Unit
) {
    val decks = viewModel.dkDecks.collectAsState(initial = listOf())
    DeckList(decks, onClick)
}

@Composable
fun HunterScreen(
    viewModel: MainViewModel,
    onClick: (String) -> Unit
) {
    val decks = viewModel.hunterDecks.collectAsState(initial = listOf())
    DeckList(decks, onClick)
}

@Composable
fun WarriorScreen(
    viewModel: MainViewModel,
    onClick: (String) -> Unit
) {
    val decks = viewModel.warriorDecks.collectAsState(initial = listOf())
    DeckList(decks, onClick)
}

@Composable
fun MageScreen(
    viewModel: MainViewModel,
    onClick: (String) -> Unit
) {
    val decks = viewModel.mageDecks.collectAsState(initial = listOf())
    DeckList(decks, onClick)
}

@Composable
fun DruidScreen(
    viewModel: MainViewModel,
    onClick: (String) -> Unit
) {
    val decks = viewModel.druidDecks.collectAsState(initial = listOf())
    DeckList(decks, onClick)
}

@Composable
fun RogueScreen(
    viewModel: MainViewModel,
    onClick: (String) -> Unit
) {
    val decks = viewModel.rogueDecks.collectAsState(initial = listOf())
    DeckList(decks, onClick)
}

@Composable
fun ShamanScreen(
    viewModel: MainViewModel,
    onClick: (String) -> Unit
) {
    val decks = viewModel.shamanDecks.collectAsState(initial = listOf())
    DeckList(decks, onClick)
}

@Composable
fun DemonhunterScreen(
    viewModel: MainViewModel,
    onClick: (String) -> Unit
) {
    val decks = viewModel.dhDecks.collectAsState(initial = listOf())
    DeckList(decks, onClick)
}

@Composable
fun PriestScreen(
    viewModel: MainViewModel,
    onClick: (String) -> Unit
) {
    val decks = viewModel.priestDecks.collectAsState(initial = listOf())
    DeckList(decks, onClick)
}

@Composable
fun PaladinScreen(
    viewModel: MainViewModel,
    onClick: (String) -> Unit
) {
    val decks = viewModel.paladinDecks.collectAsState(initial = listOf())
    DeckList(decks, onClick)
}

@Composable
fun WarlockScreen(
    viewModel: MainViewModel,
    onClick: (String) -> Unit
) {
    val decks = viewModel.warlockDecks.collectAsState(initial = listOf())
    DeckList(decks, onClick)
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun DeckList(
    decks: State<List<ManacostDeckInfo>>,
    onClick: (String) -> Unit
) {
    if (decks.value.isEmpty()) {
        LazyColumn {
            items(25) {
                DeckRow(
                    modifier = Modifier.shimmerBackground(),
                    deck = ManacostDeckInfo(HearthstoneClass.UNKNOWN, "", "", 0, 0, 0),
                    onClick = {}
                )
            }
        }
    } else {
        val groupedItems = decks.value.groupBy { it.date }
        val today = System.currentTimeMillis().convertLongToFormattedDate(DAY_MONTH)
        LazyColumn {
            groupedItems.forEach { (day, list) ->
                stickyHeader {
                    Text(
                        text =
                        if (today == day.convertLongToFormattedDate(DAY_MONTH))
                            stringResource(id = R.string.today)
                        else
                            day.convertLongToFormattedDate(DAY_MONTH),
                        style = MaterialTheme.typography.h6,
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.LightGray)
                            .padding(8.dp)
                    )
                }
                items(list) { deck ->
                    DeckRow(
                        deck = deck,
                        onClick = onClick
                    )
                }
            }
        }
    }
}