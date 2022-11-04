package com.freyapps.hearthstonedeckviewer.ui.top_decks_screen

import android.annotation.SuppressLint
import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.freyapps.hearthstonedeckviewer.ui.MainViewModel
import com.freyapps.hearthstonedeckviewer.R
import com.freyapps.hearthstonedeckviewer.common.DAY_MONTH
import com.freyapps.hearthstonedeckviewer.common.convertLongToFormattedDate
import com.freyapps.hearthstonedeckviewer.data.models.local.HearthstoneClass
import com.freyapps.hearthstonedeckviewer.data.models.local.ManacostDeckInfo
import com.freyapps.hearthstonedeckviewer.ui.theme.HearthstoneDeckViewerTheme
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TopDecksScreen(
    viewModel: MainViewModel,
    onClick: (String) -> Unit
) {

    val error = viewModel.error
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    val pagerState = rememberPagerState(pageCount = 10)
    val tabList = listOf(
        TabItem.Hunter,
        TabItem.Mage,
        TabItem.Warrior,
        TabItem.Druid,
        TabItem.Rogue,
        TabItem.Shaman,
        TabItem.Demonhunter,
        TabItem.Priest,
        TabItem.Paladin,
        TabItem.Warlock
    )

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = { Tabs(pagerState, tabList) },
        topBar = { TopBar(pagerState, tabList, viewModel) }
    ) { innerPadding ->

        HorizontalPager(
            modifier = Modifier.padding(innerPadding),
            state = pagerState
        ) {
            when (pagerState.currentPage) {
                0 -> {
                    HunterScreen(viewModel = viewModel, onClick = onClick)
                }
                1 -> {
                    MageScreen(viewModel = viewModel, onClick = onClick)
                }
                2 -> {
                    WarriorScreen(viewModel = viewModel, onClick = onClick)
                }
                3 -> {
                    DruidScreen(viewModel = viewModel, onClick = onClick)
                }
                4 -> {
                    RogueScreen(viewModel = viewModel, onClick = onClick)
                }
                5 -> {
                    ShamanScreen(viewModel = viewModel, onClick = onClick)
                }
                6 -> {
                    DHScreen(viewModel = viewModel, onClick = onClick)
                }
                7 -> {
                    PriestScreen(viewModel = viewModel, onClick = onClick)
                }
                8 -> {
                    PaladinScreen(viewModel = viewModel, onClick = onClick)
                }
                9 -> {
                    WarlockScreen(viewModel = viewModel, onClick = onClick)
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

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TopBar(pagerState: PagerState, tabList: List<TabItem>, viewModel: MainViewModel) {
    val isLoading = viewModel.isLoading
    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = if (isLoading) 360F else 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing)
        )
    )

    TopAppBar(
        title = {
            Text(
                text = stringResource(id = tabList[pagerState.currentPage].title)
            )
        },
        backgroundColor = colorResource(id = tabList[pagerState.currentPage].color),
        contentColor = Color.White,
        actions = {
            IconButton(
                enabled = !isLoading,
                onClick = {
                    if (isLoading) {
                        // do nothing
                    } else {
                        viewModel.refreshTopDecksByClass(tabList[pagerState.currentPage].hearthstoneClass)
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "refresh decks",
                    modifier = Modifier.rotate(angle)
                )
            }
        }
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Tabs(pagerState: PagerState, tabList: List<TabItem>) {

    val scope = rememberCoroutineScope()

    ScrollableTabRow(
        selectedTabIndex = pagerState.currentPage,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                height = 2.dp,
                color = Color.White
            )
        }
    ) {
        tabList.forEachIndexed { index, _ ->
            Tab(
                modifier = Modifier.background(colorResource(id = tabList[index].color)),
                text = {
                    Text(
                        text = stringResource(id = tabList[index].title),

                        color = if (pagerState.currentPage == index) Color.White else Color.LightGray
                    )
                },
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
            )
        }
    }
}

@Composable
fun DeckRow(
    deck: ManacostDeckInfo,
    onClick: (String) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick(deck.code)
            }
    ) {
        val (name, likesRow, divider) = createRefs()
        val horizontalMargin = dimensionResource(id = R.dimen.horizontal_margin_small)
        val verticalMargin = dimensionResource(id = R.dimen.vertical_margin_small)

        Divider(
            modifier = Modifier
                .constrainAs(divider) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                },
            thickness = 1.dp
        )
        Text(
            modifier = Modifier
                .constrainAs(name) {
                    start.linkTo(parent.start, horizontalMargin)
                    end.linkTo(likesRow.start, horizontalMargin)
                    top.linkTo(parent.top, verticalMargin)
                    bottom.linkTo(divider.top)
                    width = Dimension.fillToConstraints
                },
            text = deck.name
        )

        Row(
            modifier = Modifier
                .constrainAs(likesRow) {
                    end.linkTo(parent.end, horizontalMargin)
                    top.linkTo(parent.top, verticalMargin)
                    bottom.linkTo(divider.top)
                },
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier
                    .width(40.dp),
                text = deck.likes.toString(),
                textAlign = TextAlign.Center
            )
            Text(
                modifier = Modifier
                    .width(40.dp),
                text = deck.dislikes.toString(),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DeckRowPreview() {
    HearthstoneDeckViewerTheme {
        DeckRow(
            ManacostDeckInfo(
                HearthstoneClass.HUNTER,
                "a very very very very very very very very very very long name",
                "AAECAR8I4Z8Ex7IE57kEuNkEvuMEweME0+QE+ZIFC/T2A8OABOGkBMCsBLi2BIPIBL/TBPzbBMzkBNDkBNLkBAA=",
                25,
                36,
                System.currentTimeMillis()
            )
        ) { }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun DeckList(
    decks: State<List<ManacostDeckInfo>>,
    onClick: (String) -> Unit
) {
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
fun DHScreen(
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