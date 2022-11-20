package com.freyapps.hearthstonedeckviewer.ui.card_dialog

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.freyapps.hearthstonedeckviewer.R
import com.freyapps.hearthstonedeckviewer.data.models.local.CardLocal

@Composable
fun CardDialog(card: CardLocal?, setShowDialog: (Boolean) -> Unit) {
    Dialog(
        onDismissRequest = { setShowDialog(false) }
    ) {
        Surface(
            modifier = Modifier
                .height(393.dp)
                .width(284.dp),
            shape = RoundedCornerShape(16.dp),
            color = Color.White.copy(alpha = 0.0f)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(card?.image)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(id = R.drawable.placeholder_card),
                contentDescription = card?.name,
                contentScale = ContentScale.FillBounds
            )
        }
    }
}