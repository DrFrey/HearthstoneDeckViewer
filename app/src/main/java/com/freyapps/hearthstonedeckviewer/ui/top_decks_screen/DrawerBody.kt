package com.freyapps.hearthstonedeckviewer.ui.top_decks_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.freyapps.hearthstonedeckviewer.BuildConfig
import com.freyapps.hearthstonedeckviewer.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.freyapps.hearthstonedeckviewer.ui.theme.Typography

@Composable
fun DrawerBody(
    menuItems: List<MenuItem>,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    modifier: Modifier = Modifier,
    onItemClick: (MenuItem) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier
                .padding(15.dp),
            shape = RoundedCornerShape(20.dp),
            elevation = 10.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.hearthstone),
                    contentDescription = null,
                    modifier = Modifier
                        .width(96.dp)
                        .height(96.dp)
                )
                Text(
                    text = LocalContext.current.getString(R.string.app_name
                    ),
                    style = Typography.subtitle2
                )
                Text(
                    text = LocalContext.current.getString(R.string.version, BuildConfig.VERSION_NAME
                    ),
                    style = Typography.subtitle2
                )
            }

        }
    }

    LazyColumn(
        modifier = modifier
    ) {
        items(menuItems) { item ->
            DrawerItem(
                item,
                modifier = modifier
            ) {
                scope.launch {
                    scaffoldState.drawerState.close()
                }
                onItemClick(item)
            }
        }
    }
}