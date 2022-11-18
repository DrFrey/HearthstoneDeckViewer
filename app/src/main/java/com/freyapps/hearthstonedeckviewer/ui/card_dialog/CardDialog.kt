package com.freyapps.hearthstonedeckviewer.ui.card_dialog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.freyapps.hearthstonedeckviewer.data.models.local.CardLocal
import com.freyapps.hearthstonedeckviewer.ui.theme.Typography

@Composable
fun CardDialog(card: CardLocal?, isLoading: Boolean, setShowDialog: (Boolean) -> Unit) {
    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                if (!isLoading && card != null) {
                    Column {
                        Text(
                            text = card.name,
                            style = Typography.h6
                        )
                        Divider()
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(card.image)
                                .crossfade(true)
                                .build(),
                            contentDescription = card.name,
                            alignment = Alignment.Center,
                            contentScale = ContentScale.Crop
                        )
                        Divider()
                        Text(
                            text = card.text,
                            style = Typography.body1
                        )
                        Divider()
                        Text(
                            text = card.flavorText,
                            style = Typography.body1
                        )
                    }
                } else {
                    CircularProgressIndicator()
                }
            }
        }
    }
}