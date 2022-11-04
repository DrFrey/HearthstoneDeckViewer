package com.freyapps.hearthstonedeckviewer.ui.deck_screen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.freyapps.hearthstonedeckviewer.R
import com.freyapps.hearthstonedeckviewer.data.models.remote.Card
import com.freyapps.hearthstonedeckviewer.ui.DeckViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DeckScreen(viewModel: DeckViewModel) {

    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val error = viewModel.error
    val isLoading = viewModel.isLoading

    Scaffold(
        scaffoldState = scaffoldState
    ) {
        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator()
            }
        } else {
            val cards = viewModel.deck?.cards?.sortedBy {
                it.manaCost
            } ?: listOf()
            if (cards.isEmpty()) {
                LazyColumn {
                    items(cards) { card ->
                        TextCardRow(card = card)
                    }
                }
            } else {
                val cardsGroupedByCost = cards.groupBy { it.manaCost }
                cardsGroupedByCost.forEach { (qty, list) ->
                    LazyColumn {
                        items(list) { card ->
                            TextCardRow(card = card, qty = qty)
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
fun TextCardRow(card: Card, qty: Int = 1) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(30.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            modifier = Modifier
                .width(50.dp)
                .wrapContentSize(Alignment.Center),
            text = card.manaCost.toString(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h6
        )
        Text(
            text = card.name.toString(),
            style = MaterialTheme.typography.h6
        )
        Text(
            text = qty.toString(),
            style = MaterialTheme.typography.h6
        )
    }
    Divider()
}

@Composable
fun CardRow(card: Card) {
    ConstraintLayout(
        modifier = Modifier
            .background(
                color = colorResource(
                    id = R.color.card_list_background
                )
            )
            .height(44.dp)
            .fillMaxWidth()
    ) {
        Log.d("CardRow", "card image = ${card.cropImage}")
        val (left_img, mid_img, right_img, mask, name, img, manacost) = createRefs()
        Image(
            modifier = Modifier
                .constrainAs(img) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end, margin = 30.dp)
                }
                .width(150.dp)
                .height(44.dp),
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(card.cropImage)
                    .crossfade(true)
                    .build()
            ),
            contentDescription = "card img",
            alignment = Alignment.Center,
            contentScale = ContentScale.FillWidth
        )
        Image(
            modifier = Modifier
                .constrainAs(right_img) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }
                .width(50.dp)
                .fillMaxHeight(),
            painter = painterResource(id = R.drawable.card_list_right),
            contentDescription = "card right"
        )
        Image(
            modifier = Modifier
                .constrainAs(mask) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end, margin = 30.dp)
                }
                .width(200.dp)
                .height(64.dp),
            painter = painterResource(id = R.drawable.card_list_mask),
            contentDescription = "card mask",
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center
        )
        Image(
            modifier = Modifier
                .constrainAs(mid_img) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .padding(horizontal = 25.dp)
                .fillMaxSize(),
            painter = painterResource(id = R.drawable.card_list_middle),
            contentDescription = "card middle",
            contentScale = ContentScale.FillBounds
        )
        Image(
            modifier = Modifier
                .constrainAs(left_img) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                }
                .width(50.dp)
                .fillMaxHeight(),
            painter = painterResource(id = R.drawable.card_list_left),
            contentDescription = "card left"
        )
        Text(
            modifier = Modifier
                .constrainAs(manacost) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                }
                .width(50.dp)
                .wrapContentSize(Alignment.Center),
            text = card.manaCost.toString(),
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier
                .constrainAs(name) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start, margin = 75.dp)
                }
                .width(250.dp)
                .wrapContentHeight(align = Alignment.CenterVertically, unbounded = true),
            text = card.name.toString(),
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Start
        )
    }
}

@Preview
@Composable
fun PreviewCardRow() {
    TextCardRow(
        card = Card(
            1, "artist name", 10, 1, 1,
            null, 1, 1, "", null, 30,
            "flavor text", 30, 1, null, null, null,
            10, 2, null, "Mock Card", 1, null,
            3, "mock text on card"
        )
    )
}

