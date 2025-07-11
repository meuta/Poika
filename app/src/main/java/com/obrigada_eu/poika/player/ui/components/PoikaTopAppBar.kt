package com.obrigada_eu.poika.player.ui.components


import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import com.obrigada_eu.poika.R
import com.obrigada_eu.poika.ui.theme.Dimens
import kotlin.String
import kotlin.collections.Map

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PoikaTopAppBar(
    menuItems: Map<String, () -> Unit>,
    menuIconOnclick: () -> Unit,
    onDismissRequest: () -> Unit,
    expanded: Boolean,
) {

    TopAppBar(
        title = { Text(text = stringResource(R.string.app_name)) },
        actions = {
            IconButton(onClick = menuIconOnclick) {
                Icon(Icons.Default.MoreVert, contentDescription = null)
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = onDismissRequest,
                offset = DpOffset(Dimens.MenuOffsetX, Dimens.MenuOffsetY),
                modifier = Modifier.widthIn(Dimens.MenuWidthMin, Dimens.MenuWidthMax)
            ) {

                val items: Map<String, () -> Unit> = menuItems

                items.forEach { (labelRes, action) ->
                    CustomDropdownMenuItem(
                        text = labelRes,
                        onClick = action,
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = Color.White,
            actionIconContentColor = Color.White
        ),
    )
}

@Preview
@Composable
fun PoikaTopAppBarPreview() {
    PoikaTopAppBar(
        menuItems = mapOf(
            R.string.choose_song to {},
            R.string.delete_song to {},
            R.string.help to {}
        ).mapKeys { stringResource(it.key) },
        menuIconOnclick = {},
        onDismissRequest = {},
        expanded = true
    )
}