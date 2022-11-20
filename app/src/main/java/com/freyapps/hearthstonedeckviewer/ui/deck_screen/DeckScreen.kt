package com.freyapps.hearthstonedeckviewer.ui.deck_screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.freyapps.hearthstonedeckviewer.data.models.remote.Card
import com.freyapps.hearthstonedeckviewer.ui.DeckViewModel
import com.freyapps.hearthstonedeckviewer.ui.card_dialog.CardDialog
import com.freyapps.hearthstonedeckviewer.ui.theme.Typography

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DeckScreen(viewModel: DeckViewModel) {

    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val error = viewModel.error
    val isLoading = viewModel.isLoading

    val showCardDialog = remember { mutableStateOf(false) }
    val cardToShow = viewModel.card

    if (showCardDialog.value) {
        CardDialog(card = cardToShow) {
            showCardDialog.value = it
        }
    }

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            val cards = viewModel.deck?.cards ?: mapOf()
            Log.d("DeckScreen", "cards: $cards")
            if (cards.isEmpty()) {
                Column {
                    Text(text = "Card list is empty")
                }
            } else {
                val cardsSortedByCost = cards.keys.sortedBy { it.manaCost }
                LazyColumn {
                    items(cardsSortedByCost) { card ->
                        TextCardRow(card = card, qty = cards[card] ?: 9999) {
                            viewModel.getCard(it)
                            showCardDialog.value = true
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(key1 = error) {
        if (error.isNotEmpty()) {
            when (scaffoldState.snackbarHostState.showSnackbar(error)) {
                SnackbarResult.Dismissed -> viewModel.clearErrorMessage()
                else -> {}
            }
        }
    }
}

@Composable
fun TextCardRow(
    card: Card,
    qty: Int,
    onClick: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp)
            .clickable {
                onClick(card.slug)
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                modifier = Modifier
                    .width(50.dp)
                    .wrapContentSize(Alignment.Center),
                text = card.manaCost.toString(),
                textAlign = TextAlign.Center,
                style = Typography.body1
            )
            Text(
                text = card.name.toString(),
                style = Typography.body1
            )
        }
        Text(
            modifier = Modifier
                .width(50.dp)
                .wrapContentSize(Alignment.Center),
            text = qty.toString(),
            textAlign = TextAlign.Center,
            style = Typography.body1
        )
    }
    Divider()
}

@Preview
@Composable
fun PreviewCardRow() {
    TextCardRow(
        card = Card(
            1, "artist name", 10, 1, 1,
            null, 1, 1, "", null, 30,
            "flavor text", 30, 1, null, null, null,
            10, 2, null, "Mock Card", 1, "",
            3, "mock text on card"
        ),
        1
    ) {}
}

