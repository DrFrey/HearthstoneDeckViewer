package com.freyapps.hearthstonedeckviewer.ui.top_decks_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.freyapps.hearthstonedeckviewer.R
import com.freyapps.hearthstonedeckviewer.data.models.local.HearthstoneClass
import com.freyapps.hearthstonedeckviewer.data.models.local.ManacostDeckInfo
import com.freyapps.hearthstonedeckviewer.ui.theme.HearthstoneDeckViewerTheme


@Composable
fun DeckRow(
    modifier: Modifier = Modifier,
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
            modifier = modifier
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
                modifier = modifier
                    .width(40.dp),
                text = deck.likes.toString(),
                textAlign = TextAlign.Center
            )
            Text(
                modifier = modifier
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
            Modifier,
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
