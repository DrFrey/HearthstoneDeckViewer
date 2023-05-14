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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ClassScreen(
    viewModel: MainViewModel,
    hearthstoneClass: HearthstoneClass,
    onClick: (String) -> Unit
) {
    viewModel.currentClass = hearthstoneClass
    val decks = viewModel.decks.collectAsState(initial = listOf())
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